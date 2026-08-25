const profileFields = [
    'nama_ilmiah', 'common_name',
    'family', 'genus', 'kingdom',
    'kategori', 'difficulty',
    'fullsize', 'thumbnail', 'taxon',
    'common_names', 'min_panen',
    'max_panen', 'ph', 'temp',
    'description', 'prune_guide'
];
const careFields = [
    'watering', 'pruning', 'fertilization',
    'sunlight', 'pest_disease_management'
];
const productFields = [
        'rumah_tangga', 'komersial', 'industri'
    ]
;
let selected = null, originalName = null;
const find = s => document.querySelector(s);
const label = k => k.replaceAll('_', ' ').replace(/([A-Z])/g, ' $1').replace(/^./, c => c.toUpperCase());

async function request(url, options) {
    const res = await fetch(url, options);
    if (!res.ok) throw new Error((await res.text()) || res.statusText);
    return res.status === 204 ? null : res.json();
}

async function loadList() {
    const items = await request('/api/plants?query=' + encodeURIComponent(find('#search').value));
    find('#count').textContent = `${items.length} plants`;
    find('#plantList').replaceChildren(...items.map(item => {
        const button = document.createElement('button');
        button.className = 'plant';
        button.innerHTML = `<strong>${escapeHtml(item.scientificName)}</strong><small>${escapeHtml(item.commonName || item.family || 'No common name')}</small>`;
        button.onclick = () => openPlant(item.scientificName);
        return button;
    }));
}

function escapeHtml(s) {
    const d = document.createElement('div');
    d.textContent = s || '';
    return d.innerHTML;
}

async function openPlant(name) {
    selected = await request('/api/plants/' + encodeURIComponent(name));
    originalName = name;
    render(false);
}

function addField(container, key, value, area = false) {
    const wrapper = document.createElement('label'), title = document.createElement('span');
    title.textContent = label(key);
    wrapper.append(title);
    const input = document.createElement(area ? 'textarea' : 'input');
    input.name = `${container}:${key}`;
    input.value = value ?? '';
    wrapper.append(input);
    find(container).append(wrapper);
}

function render(isNew) {
    find('#empty').hidden = true;
    find('#editor').hidden = false;
    find('#profile').replaceChildren();
    find('#care').replaceChildren();
    find('#product').replaceChildren();
    find('#ecocrop').replaceChildren();
    find('#mode').textContent = isNew ? 'NEW PLANT' : 'EDIT PLANT';
    find('#delete').hidden = isNew;
    find('#message').textContent = '';
    profileFields.forEach(k => addField('#profile', k, selected.json[k], ['description', 'common_names', 'prune_guide'].includes(k)));
    careFields.forEach(k => addField('#care', k, selected.json.plant_care?.[k], true));
    productFields.forEach(k => addField('#product', k, selected.json.product_system?.[k], true));
    Object.keys(selected.ecocrop).forEach(k => addField('#ecocrop', k, selected.ecocrop[k], k === 'COMNAME' || k === 'SYNO' || k === 'CLIZ'));
    find('#title').textContent = selected.json.nama_ilmiah || 'Untitled plant';
}

function values(container) {
    const out = {};
    document.querySelectorAll(container + ' [name]').forEach(i => out[i.name.split(':')[1]] = i.value || null);
    return out;
}

find('#editor').onsubmit = async e => {
    e.preventDefault();
    const json = {
        ...selected.json, ...values('#profile'),
        plant_care: values('#care'),
        product_system: values('#product')
    };
    const ecocrop = {...selected.ecocrop, ...values('#ecocrop')};
    find('#message').textContent = 'Saving…';
    try {
        const url = originalName ? '/api/plants/' + encodeURIComponent(originalName) : '/api/plants';
        selected = await request(url, {
            method: originalName ? 'PUT' : 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({json, ecocrop})
        });
        originalName = selected.json.nama_ilmiah;
        find('#message').textContent = 'Saved to plants.json and EcoCrop_DB.csv.';
        render(false);
        await loadList();
    } catch (err) {
        find('#message').textContent = 'Could not save: ' + err.message;
    }
};
find('#newPlant').onclick = async () => {
    originalName = null;
    selected = await request('/api/plants/template');
    render(true);
};
find('#delete').onclick = async () => {
    if (!confirm(`Remove ${originalName} from both files?`)) return;
    try {
        await request('/api/plants/' + encodeURIComponent(originalName), {method: 'DELETE'});
        find('#editor').hidden = true;
        find('#empty').hidden = false;
        await loadList();
    } catch (err) {
        alert(err.message)
    }
};
let timer;
find('#search').oninput = () => {
    clearTimeout(timer);
    timer = setTimeout(loadList, 180)
};
loadList().catch(err => find('#plantList').textContent = err.message);
