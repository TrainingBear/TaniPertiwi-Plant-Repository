async function init() {
    async function createProfilePanels() {
        let categories = await request("/api/id/complete/categories")
        return [
            {
                title: 'Identitas', fields: [
                    ['nama_ilmiah', 'Nama ilmiah', 'Wajib diisi; kunci pemetaan pada repository.'],
                    ['common_name', 'Nama umum'],
                    ['kategori', 'Kategori', 'Pilih jenis utama tanaman.', 'select', categories],
                    ['difficulty', 'Tingkat kesulitan', 'Pilih tingkat kesulitan perawatan.', 'select', ['EASY', 'MEDIUM', 'HARD']]
                ]
            },
            {
                title: 'Klasifikasi', fields: [
                    ['kingdom', 'Kingdom'], ['family', 'Famili'], ['genus', 'Genus'], ['taxon', 'Takson']
                ]
            },
            {
                title: 'Karakteristik tumbuh & panen', fields: [
                    ['min_panen', 'Panen minimum', 'Waktu panen tercepat, misalnya 45 hari.'],
                    ['max_panen', 'Panen maksimum', 'Waktu panen terlama, misalnya 60 hari.'],
                    ['ph', 'pH tanah', 'Rentang pH tanah yang dianjurkan.'],
                    ['temp', 'Suhu', 'Suhu tumbuh yang dianjurkan, dalam °C.']
                ]
            },
            {
                title: 'Media & deskripsi', fields: [
                    ['thumbnail', 'URL thumbnail', 'Gambar kecil untuk daftar tanaman.'],
                    ['fullsize', 'URL gambar penuh', 'Gambar utama tanaman.'],
                    ['common_names', 'Nama-nama lain', 'Pisahkan setiap nama dengan koma.', 'textarea'],
                    ['description', 'Deskripsi singkat', 'Ringkasan tanaman untuk pengguna.', 'textarea'],
                    ['prune_guide', 'Panduan pemangkasan', 'Petunjuk khusus jika diperlukan.', 'textarea']
                ]
            }
        ]
    }

    const profilePanels = await createProfilePanels()

    const carePanels = [{
        title: 'Rutinitas perawatan', fields: [
            ['watering', 'Penyiraman', 'Contoh: 2–3 kali per minggu; biarkan lapisan tanah atas mengering.', 'textarea'],
            ['pest_disease_management', 'Kontrol hama & penyakit', 'Sebutkan pencegahan, gejala umum, atau penanganannya.', 'textarea'],
            ['fertilization', 'Pemupukan', 'Tuliskan jenis pupuk dan frekuensi pemberiannya.', 'textarea'],
            ['sunlight', 'Sinar matahari', 'Contoh: matahari penuh (6+ jam) atau teduh sebagian.', 'textarea'],
            ['pruning', 'Pemangkasan', 'Jelaskan bagian tanaman dan waktu pemangkasan.', 'textarea']
        ]
    }];

    const productPanels = [{
        title: 'Kegunaan produk', fields: [
            ['komersial', 'Komersial', 'Potensi produk atau pasar komersial.', 'textarea'],
            ['rumah_tangga', 'Rumah tangga', 'Kegunaan untuk konsumsi atau kebutuhan rumah tangga.', 'textarea'],
            ['industri', 'Industri', 'Bahan baku atau aplikasi industri.', 'textarea']
        ]
    }];

    const ecocropPanels = [
        {
            title: 'Identitas sumber', fields: [
                ['EcoPortCode', 'Kode EcoPort'], ['ScientificName', 'Nama ilmiah'], ['AUTH', 'Otoritas nama'],
                ['FAMNAME', 'Klasifikasi famili'], ['SYNO', 'Sinonim', 'Pisahkan sinonim dengan koma.', 'textarea'],
                ['COMNAME', 'Nama umum', 'Daftar nama umum dari sumber EcoCrop.', 'textarea']
            ]
        },
        {
            title: 'Habit & kategori', fields: [
                ['LIFO', 'Bentuk hidup'], ['HABI', 'Habitus'], ['LISPA', 'Siklus hidup'], ['PHYS', 'Fisiognomi'],
                ['CAT', 'Kategori tanaman'], ['PLAT', 'Sistem penanaman']
            ]
        },
        {
            title: 'Suhu, curah hujan & pH', fields: [
                ['TOPMN', 'Suhu optimal minimum', '°C.'], ['TOPMX', 'Suhu optimal maksimum', '°C.'],
                ['TMIN', 'Suhu minimum', '°C.'], ['TMAX', 'Suhu maksimum', '°C.'],
                ['ROPMN', 'Curah hujan optimal minimum', 'mm per tahun.'], ['ROPMX', 'Curah hujan optimal maksimum', 'mm per tahun.'],
                ['RMIN', 'Curah hujan minimum', 'mm per tahun.'], ['RMAX', 'Curah hujan maksimum', 'mm per tahun.'],
                ['PHOPMN', 'pH optimal minimum'], ['PHOPMX', 'pH optimal maksimum'],
                ['PHMIN', 'pH minimum'], ['PHMAX', 'pH maksimum']
            ]
        },
        {
            title: 'Lokasi, ketinggian & cahaya', fields: [
                ['LATOPMN', 'Lintang optimal minimum', 'Derajat lintang.'], ['LATOPMX', 'Lintang optimal maksimum', 'Derajat lintang.'],
                ['LATMN', 'Lintang minimum', 'Derajat lintang.'], ['LATMX', 'Lintang maksimum', 'Derajat lintang.'],
                ['ALTMX', 'Ketinggian maksimum', 'Meter di atas permukaan laut.'],
                ['LIOPMN', 'Cahaya optimal minimum'], ['LIOPMX', 'Cahaya optimal maksimum'],
                ['LIMN', 'Cahaya minimum'], ['LIMX', 'Cahaya maksimum']
            ]
        },
        {
            title: 'Kedalaman, tekstur & kesuburan tanah', fields: [
                ['DEP', 'Kedalaman tanah'], ['DEPR', 'Kedalaman tanah (referensi)'],
                ['TEXT', 'Tekstur tanah', 'Gunakan istilah tekstur dari EcoCrop.', 'textarea'],
                ['TEXTR', 'Tekstur tanah (referensi)', 'Gunakan istilah tekstur dari EcoCrop.', 'textarea'],
                ['FER', 'Kesuburan tanah'], ['FERR', 'Kesuburan tanah (referensi)']
            ]
        },
        {
            title: 'Toleransi & drainase', fields: [
                ['TOX', 'Toleransi toksisitas'], ['TOXR', 'Toksisitas (referensi)'],
                ['SAL', 'Toleransi salinitas'], ['SALR', 'Salinitas (referensi)'],
                ['DRA', 'Kebutuhan drainase'], ['DRAR', 'Drainase (referensi)']
            ]
        },
        {
            title: 'Iklim & siklus pertumbuhan', fields: [
                ['KTMPR', 'Klasifikasi Köppen utama'], ['KTMP', 'Klasifikasi Köppen'],
                ['PHOTO', 'Fotoperiode'], ['CLIZ', 'Zona iklim', 'Daftar zona iklim yang sesuai.', 'textarea'],
                ['ABITOL', 'Toleransi abiotik'], ['ABISUS', 'Kerentanan abiotik'],
                ['INTRI', 'Sifat intrusi'], ['PROSY', 'Sistem perbanyakan'],
                ['GMIN', 'Masa tumbuh minimum', 'Hari.'], ['GMAX', 'Masa tumbuh maksimum', 'Hari.']
            ]
        }
    ];
    let selected = null, originalName = null;
    const find = s => document.querySelector(s);

    async function request(url, options) {
        const res = await fetch(url, options);

        console.log("Request:", url);
        console.log("Status:", res.status);
        console.log("Response:", await res.clone().text());

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

    function addField(container, [key, title, hint, type, options], value) {
        const wrapper = document.createElement('label');
        wrapper.className = type === 'textarea' ? 'field field-wide' : 'field';
        const labelText = document.createElement('span');
        labelText.className = 'field-label';
        labelText.textContent = title;
        wrapper.append(labelText);
        if (hint) {
            const help = document.createElement('small');
            help.className = 'field-hint';
            help.textContent = hint;
            wrapper.append(help);
        }
        const input = document.createElement(type === 'textarea' ? 'textarea' : type === 'select' ? 'select' : 'input');
        input.name = `${container}:${key}`;
        if (type === 'select') {
            const emptyOption = new Option('Pilih opsi…', '');
            input.add(emptyOption);
            options.forEach(option => input.add(new Option(option, option)));
            if (value && !options.includes(value)) input.add(new Option(value, value));
        }
        input.value = value ?? '';
        wrapper.append(input);
        return wrapper;
    }

    function renderPanels(container, panels, values) {
        const target = find(container);
        panels.forEach(panel => {
            const section = document.createElement('section');
            section.className = 'field-panel';
            const heading = document.createElement('h3');
            heading.textContent = panel.title;
            const fields = document.createElement('div');
            fields.className = 'panel-fields';
            panel.fields.forEach(
                field =>
                    fields.append(
                        addField(container, field, values?.[field[0]])
                    )
            );
            section.append(heading, fields);
            target.append(section);
        });
    }

    function render(isNew) {
        // find('#empty').hidden = true;
        find('#editor').hidden = false;
        find('#profile').replaceChildren();
        find('#care').replaceChildren();
        find('#product').replaceChildren();
        find('#ecocrop').replaceChildren();
        find('#mode').textContent = isNew ? 'NEW PLANT' : 'EDIT PLANT';
        find('#delete').hidden = isNew;
        find('#message').textContent = '';
        renderPanels('#care', carePanels, selected.json.plant_care);
        renderPanels('#product', productPanels, selected.json.product_system);
        renderPanels('#ecocrop', ecocropPanels, selected.ecocrop);
        // find('#profile')
        renderPanels('#profile', profilePanels, selected.json);
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
            // find('#empty').hidden = false;
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
}

init()