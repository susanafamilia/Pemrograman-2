const {
  Document, Packer, Paragraph, TextRun, HeadingLevel,
  AlignmentType, LevelFormat, BorderStyle,
  WidthType, Table, TableRow, TableCell, ShadingType,
} = require('docx');
const fs = require('fs');

const FONT = "Times New Roman";
const SIZE_BODY = 24;
const SIZE_H1   = 28;
const SIZE_H2   = 24;

function h1(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_1, alignment: AlignmentType.CENTER,
    spacing: { before: 360, after: 240 },
    children: [new TextRun({ text, bold: true, size: SIZE_H1, font: FONT })]
  });
}
function h2(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_2,
    spacing: { before: 240, after: 120 },
    children: [new TextRun({ text, bold: true, size: SIZE_H2, font: FONT })]
  });
}
function h3(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_3,
    spacing: { before: 180, after: 100 },
    children: [new TextRun({ text, bold: true, size: SIZE_BODY, font: FONT })]
  });
}
function body(text, opts = {}) {
  return new Paragraph({
    alignment: opts.align || AlignmentType.JUSTIFIED,
    spacing: { before: 0, after: 120, line: 360 },
    indent: opts.noIndent ? {} : { firstLine: 720 },
    children: [new TextRun({ text, size: SIZE_BODY, font: FONT, bold: opts.bold || false, italics: opts.italic || false })]
  });
}
function mixed(runs, opts = {}) {
  return new Paragraph({
    alignment: opts.align || AlignmentType.JUSTIFIED,
    spacing: { before: 0, after: 120, line: 360 },
    indent: opts.noIndent ? {} : { firstLine: 720 },
    children: runs
  });
}
function r(text, opts = {}) {
  return new TextRun({ text, size: SIZE_BODY, font: FONT, bold: opts.bold || false, italics: opts.italic || false });
}
function empty() {
  return new Paragraph({ children: [new TextRun({ text: "", size: SIZE_BODY, font: FONT })] });
}
function bullet(text, ref = "bullets") {
  const children = typeof text === "string"
    ? [new TextRun({ text, size: SIZE_BODY, font: FONT })]
    : text;
  return new Paragraph({
    numbering: { reference: ref, level: 0 },
    spacing: { before: 0, after: 80, line: 360 },
    children
  });
}
function numbered(text) { return bullet(text, "decimal"); }

const bd = { style: BorderStyle.SINGLE, size: 1, color: "000000" };
const borders = { top: bd, bottom: bd, left: bd, right: bd };
function tc(text, isHeader = false, width = 4680, align = AlignmentType.LEFT) {
  return new TableCell({
    borders, width: { size: width, type: WidthType.DXA },
    shading: { fill: isHeader ? "D9E1F2" : "FFFFFF", type: ShadingType.CLEAR },
    margins: { top: 80, bottom: 80, left: 120, right: 120 },
    children: [new Paragraph({ alignment: align, children: [new TextRun({ text, size: SIZE_BODY, font: FONT, bold: isHeader })] })]
  });
}
function caption(text) {
  return new Paragraph({
    alignment: AlignmentType.CENTER,
    spacing: { before: 60, after: 200 },
    children: [new TextRun({ text, size: SIZE_BODY, font: FONT, italics: true })]
  });
}
function placeholder(text) {
  return new Paragraph({
    alignment: AlignmentType.CENTER,
    spacing: { before: 120, after: 60 },
    border: { top: bd, bottom: bd, left: bd, right: bd },
    children: [new TextRun({ text: `[ ${text} ]`, size: SIZE_BODY, font: FONT, italics: true, color: "888888" })]
  });
}

const doc = new Document({
  styles: {
    default: { document: { run: { font: FONT, size: SIZE_BODY } } },
    paragraphStyles: [
      { id: "Heading1", name: "Heading 1", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: SIZE_H1, bold: true, font: FONT },
        paragraph: { spacing: { before: 360, after: 240 }, outlineLevel: 0 } },
      { id: "Heading2", name: "Heading 2", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: SIZE_H2, bold: true, font: FONT },
        paragraph: { spacing: { before: 240, after: 120 }, outlineLevel: 1 } },
      { id: "Heading3", name: "Heading 3", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: SIZE_BODY, bold: true, font: FONT },
        paragraph: { spacing: { before: 180, after: 100 }, outlineLevel: 2 } },
    ]
  },
  numbering: {
    config: [
      { reference: "bullets", levels: [{ level: 0, format: LevelFormat.BULLET, text: "\u2022", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
      { reference: "decimal", levels: [{ level: 0, format: LevelFormat.DECIMAL, text: "%1.", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
      { reference: "alpha",   levels: [{ level: 0, format: LevelFormat.LOWER_LETTER, text: "%1.", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
    ]
  },
  sections: [{
    properties: {
      page: {
        size: { width: 11906, height: 16838 },
        margin: { top: 1440, right: 1440, bottom: 1440, left: 2016 }
      }
    },
    children: [

      // ══════════════════════════════════════
      // JUDUL BAB
      // ══════════════════════════════════════
      h1("BAB III"),
      h1("METODOLOGI PENELITIAN"),
      empty(),

      // ══════════════════════════════════════
      // 3.1 TINJAUAN PUSTAKA
      // ══════════════════════════════════════
      h2("3.1 Tinjauan Pustaka"),
      empty(),
      body(
        "Tinjauan pustaka merupakan kajian terhadap penelitian-penelitian terdahulu yang relevan dengan topik yang sedang diteliti. Kajian ini bertujuan untuk memetakan perkembangan ilmu pengetahuan terkait, mengidentifikasi celah penelitian, serta menjadikan hasil penelitian sebelumnya sebagai landasan dan pembanding dalam pengembangan sistem yang dibangun pada Kerja Praktek ini."
      ),
      empty(),

      h3("3.1.1 Penelitian Terdahulu"),
      empty(),
      body(
        "Herdiansah, Borman, dan Maylinda (2021) dalam penelitian berjudul \"Sistem Informasi Monitoring dan Reporting Quality Control Proses Laminating Berbasis Web Framework Laravel\" yang diterbitkan pada Jurnal Tekno Kompak Vol. 15 No. 2 mengembangkan sistem informasi berbasis web menggunakan framework Laravel untuk mendukung proses monitoring quality control di lingkungan industri. Hasil penelitian menunjukkan bahwa Laravel mampu memberikan solusi pengembangan web yang efisien, terstruktur, dan mudah dipelihara berkat arsitektur MVC yang dimilikinya. Relevansi dengan penelitian ini terletak pada penggunaan framework Laravel sebagai fondasi utama pengembangan sistem."
      ),
      body(
        "Aipina dan Witriyono (2022) dalam penelitian berjudul \"Pemanfaatan Framework Laravel dan Framework Bootstrap pada Pembangunan Aplikasi Penjualan Hijab Berbasis Web\" yang diterbitkan pada Jurnal Media Infotama Vol. 18 No. 1 membangun aplikasi penjualan berbasis web yang mengintegrasikan Laravel sebagai back-end dan Bootstrap sebagai framework CSS untuk tampilan antarmuka. Penelitian ini relevan sebagai referensi kombinasi teknologi Laravel dengan framework front-end dalam membangun aplikasi web yang responsif dan fungsional."
      ),
      body(
        "Siburian dan Latifah (2023) dalam penelitian berjudul \"Penerapan Metode Waterfall dalam Perancangan Sistem Informasi Berbasis Web pada PT. Garuda Inti Sentosa untuk Meningkatkan Penjualan\" yang diterbitkan pada Journal of Information System, Applied, Management, Accounting and Research (JISAMAR) Vol. 7 No. 4 menerapkan metode Waterfall dalam pengembangan sistem informasi penjualan berbasis web untuk perusahaan yang belum memiliki platform digital sebelumnya. Kondisi awal perusahaan yang belum memiliki sistem digital memiliki kesamaan yang kuat dengan kondisi PT. Intect Teknologi Indonesia, sehingga penelitian ini menjadi acuan penting dalam penyusunan Kerja Praktek ini."
      ),
      body(
        "Anis, Wahyudi, dan Kurniawan (2024) dalam penelitian berjudul \"Metode Waterfall dalam Pengembangan Sistem Inventaris guna Meningkatkan Efisiensi Manajemen Stok Barang\" yang diterbitkan pada Jurnal Teknologi Dan Sistem Informasi Bisnis menguraikan penerapan metode Waterfall secara sistematis dalam pengembangan sistem informasi pengelolaan stok. Penelitian ini relevan sebagai referensi penerapan Waterfall dalam konteks manajemen data produk, yang menjadi salah satu fitur utama sistem yang dikembangkan dalam Kerja Praktek ini."
      ),
      body(
        "Muthia Kansha (2023) dalam penelitian berjudul \"Analisis Perbandingan Struktur dan Performa Framework Codeigniter dan Laravel dalam Pengembangan Web Application\" yang diterbitkan pada Jurnal Teknik Informatika STMIK Antar Bangsa Vol. 9 No. 1 membandingkan performa dan struktur kedua framework PHP terpopuler. Hasil penelitian menyimpulkan bahwa Laravel unggul dalam hal keamanan, struktur kode, dan kemudahan pemeliharaan untuk proyek berskala menengah hingga besar, yang memperkuat alasan pemilihan Laravel dalam pengembangan sistem pada Kerja Praktek ini."
      ),
      empty(),

      // Tabel ringkasan penelitian terdahulu
      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [360, 2160, 2160, 2160, 1800],
        rows: [
          new TableRow({ children: [
            tc("No.", true, 360, AlignmentType.CENTER),
            tc("Peneliti & Tahun", true, 2160),
            tc("Judul", true, 2160),
            tc("Metode/Teknologi", true, 2160),
            tc("Relevansi", true, 1800),
          ]}),
          new TableRow({ children: [
            tc("1", false, 360, AlignmentType.CENTER),
            tc("Herdiansah, Borman & Maylinda (2021)", false, 2160),
            tc("Sistem Informasi Monitoring dan Reporting QC Proses Laminating Berbasis Web Framework Laravel", false, 2160),
            tc("Laravel, PHP, MySQL", false, 2160),
            tc("Penggunaan Laravel sebagai framework utama", false, 1800),
          ]}),
          new TableRow({ children: [
            tc("2", false, 360, AlignmentType.CENTER),
            tc("Aipina & Witriyono (2022)", false, 2160),
            tc("Pemanfaatan Framework Laravel dan Bootstrap pada Aplikasi Penjualan Berbasis Web", false, 2160),
            tc("Laravel, Bootstrap, PHP", false, 2160),
            tc("Kombinasi Laravel dan front-end framework untuk web responsif", false, 1800),
          ]}),
          new TableRow({ children: [
            tc("3", false, 360, AlignmentType.CENTER),
            tc("Siburian & Latifah (2023)", false, 2160),
            tc("Penerapan Metode Waterfall dalam Perancangan Sistem Informasi Berbasis Web pada PT. Garuda Inti Sentosa", false, 2160),
            tc("Waterfall, PHP, MySQL", false, 2160),
            tc("Waterfall pada perusahaan belum memiliki sistem digital", false, 1800),
          ]}),
          new TableRow({ children: [
            tc("4", false, 360, AlignmentType.CENTER),
            tc("Anis, Wahyudi & Kurniawan (2024)", false, 2160),
            tc("Metode Waterfall dalam Pengembangan Sistem Inventaris untuk Meningkatkan Efisiensi Manajemen Stok Barang", false, 2160),
            tc("Waterfall, Web-based", false, 2160),
            tc("Pengelolaan data dan stok produk dengan Waterfall", false, 1800),
          ]}),
          new TableRow({ children: [
            tc("5", false, 360, AlignmentType.CENTER),
            tc("Muthia Kansha (2023)", false, 2160),
            tc("Analisis Perbandingan Struktur dan Performa Framework Codeigniter dan Laravel dalam Pengembangan Web Application", false, 2160),
            tc("Laravel, CodeIgniter, PHP", false, 2160),
            tc("Justifikasi pemilihan Laravel sebagai framework", false, 1800),
          ]}),
        ]
      }),
      caption("Tabel 3.1 Ringkasan Penelitian Terdahulu"),
      empty(),

      // ══════════════════════════════════════
      // 3.2 LANDASAN TEORI
      // ══════════════════════════════════════
      h2("3.2 Landasan Teori"),
      empty(),
      body(
        "Landasan teori merupakan kumpulan konsep, definisi, dan teori yang menjadi acuan ilmiah dalam pelaksanaan penelitian. Pada sub-bab ini diuraikan teori-teori yang relevan dengan pengembangan e-katalog produk berbasis web untuk PT. Intect Teknologi Indonesia."
      ),
      empty(),

      h3("3.2.1 Sistem Informasi"),
      body(
        "Sistem informasi adalah suatu sistem yang mengumpulkan, memproses, menyimpan, menganalisis, dan menyebarkan informasi untuk tujuan tertentu. Dalam konteks organisasi bisnis, sistem informasi berfungsi sebagai infrastruktur yang mendukung pengambilan keputusan, koordinasi, pengendalian, analisis, dan visualisasi proses dalam suatu organisasi. Sistem informasi berbasis web memanfaatkan teknologi internet sebagai media penyampaian informasi sehingga dapat diakses secara luas tanpa batasan geografis dan waktu."
      ),
      body(
        "Komponen utama sistem informasi mencakup perangkat keras (hardware), perangkat lunak (software), data, prosedur, dan pengguna (brainware). Kelima komponen ini saling berinteraksi untuk menghasilkan output informasi yang berguna bagi pengguna. Dalam penelitian ini, sistem informasi yang dimaksud adalah sistem e-katalog produk berbasis web yang dirancang untuk menyajikan informasi produk PT. Intect Teknologi Indonesia secara terstruktur dan mudah diakses oleh calon pelanggan."
      ),
      empty(),

      h3("3.2.2 E-Katalog"),
      body(
        "E-katalog atau katalog elektronik merupakan suatu sistem informasi yang menampilkan daftar produk atau layanan secara digital melalui media elektronik, khususnya berbasis web. E-katalog menyajikan informasi produk secara lengkap meliputi nama produk, deskripsi, spesifikasi teknis, gambar, dan ketersediaan stok dalam format yang terstruktur dan mudah dinavigasi oleh pengguna."
      ),
      body(
        "Keunggulan e-katalog dibandingkan katalog fisik konvensional antara lain adalah kemampuannya untuk diperbarui secara real-time, dapat diakses kapan saja dan di mana saja melalui jaringan internet, tidak memerlukan biaya cetak, serta dapat menjangkau pasar yang lebih luas. Bagi perusahaan manufaktur seperti PT. Intect Teknologi Indonesia, e-katalog menjadi instrumen strategis untuk memperkenalkan lini produk kepada calon pelanggan dari segmen pendidikan, bisnis, dan industri secara efisien dan profesional."
      ),
      empty(),

      h3("3.2.3 Website"),
      body(
        "Website merupakan kumpulan halaman web yang saling terhubung dan dapat diakses melalui jaringan internet menggunakan browser. Setiap website memiliki alamat unik yang disebut URL (Uniform Resource Locator) dan dihosting pada sebuah web server. Berdasarkan sifatnya, website dibedakan menjadi website statis yang menampilkan konten tetap, dan website dinamis yang kontennya dapat berubah sesuai interaksi pengguna atau data dari basis data."
      ),
      body(
        "Sistem e-katalog yang dikembangkan dalam penelitian ini termasuk ke dalam kategori website dinamis, di mana konten produk yang ditampilkan bersumber dari basis data MySQL dan dapat dikelola secara real-time oleh administrator perusahaan melalui panel administrasi yang terintegrasi."
      ),
      empty(),

      h3("3.2.4 PHP (Hypertext Preprocessor)"),
      mixed([
        r("PHP ("), r("Hypertext Preprocessor", { italic: true }), r(") merupakan bahasa pemrograman "), r("server-side", { italic: true }),
        r(" yang bersifat "), r("open-source", { italic: true }), r(" dan banyak digunakan dalam pengembangan aplikasi web dinamis. PHP dieksekusi di sisi server sehingga pengguna hanya menerima output berupa HTML tanpa dapat melihat kode sumber PHP secara langsung. Keunggulan PHP meliputi kemudahan integrasi dengan berbagai sistem manajemen basis data, kompatibilitas dengan berbagai web server, serta dukungan komunitas pengembang yang sangat luas di seluruh dunia.")
      ]),
      body(
        "Dalam pengembangan sistem e-katalog ini, PHP digunakan sebagai bahasa pemrograman utama yang menangani logika bisnis aplikasi, mulai dari pemrosesan permintaan pengguna, interaksi dengan basis data MySQL, hingga pengembalian respons dalam format HTML kepada browser pengguna."
      ),
      empty(),

      h3("3.2.5 Framework Laravel"),
      mixed([
        r("Laravel merupakan "), r("framework", { italic: true }), r(" aplikasi web berbasis PHP yang bersifat "), r("open-source", { italic: true }),
        r(" dan mengikuti pola arsitektur "), r("Model-View-Controller (MVC)", { italic: true }),
        r(". Laravel dirancang untuk mempermudah dan mempercepat proses pengembangan aplikasi web dengan menyediakan berbagai fitur bawaan yang komprehensif, seperti sistem autentikasi, manajemen rute, templating dengan Blade, pengelolaan migrasi basis data, dan Eloquent ORM untuk interaksi dengan basis data secara lebih elegan dan efisien.")
      ]),
      body(
        "Arsitektur MVC pada Laravel membagi aplikasi menjadi tiga lapisan utama, yaitu Model yang menangani logika data dan interaksi dengan basis data, View yang bertanggung jawab atas tampilan antarmuka pengguna, dan Controller yang berfungsi sebagai perantara antara Model dan View dalam memproses permintaan pengguna. Pemisahan tanggung jawab ini menghasilkan kode yang lebih terstruktur, mudah dipelihara, dan mudah dikembangkan."
      ),
      empty(),

      h3("3.2.6 MySQL"),
      mixed([
        r("MySQL merupakan sistem manajemen basis data relasional ("), r("Relational Database Management System/RDBMS", { italic: true }),
        r(") yang bersifat "), r("open-source", { italic: true }),
        r(" dan paling banyak digunakan dalam pengembangan aplikasi web. MySQL menggunakan bahasa SQL ("), r("Structured Query Language", { italic: true }),
        r(") untuk melakukan operasi terhadap data, meliputi operasi membuat, membaca, memperbarui, dan menghapus data (CRUD). MySQL dikenal dengan performanya yang handal, skalabilitasnya yang tinggi, dan kemudahannya dalam diintegrasikan dengan berbagai bahasa pemrograman termasuk PHP.")
      ]),
      body(
        "Dalam sistem e-katalog ini, MySQL digunakan sebagai tempat penyimpanan seluruh data aplikasi, meliputi data produk, kategori produk, informasi stok, serta data akun administrator. Seluruh interaksi antara aplikasi Laravel dengan basis data MySQL dilakukan melalui Eloquent ORM yang menyediakan antarmuka pemrograman yang bersih dan ekspresif."
      ),
      empty(),

      h3("3.2.7 HTML, CSS, dan JavaScript"),
      mixed([
        r("HTML ("), r("HyperText Markup Language", { italic: true }), r(") merupakan bahasa markah standar yang digunakan untuk membangun struktur halaman web. CSS ("), r("Cascading Style Sheets", { italic: true }),
        r(") digunakan untuk mengatur tampilan visual halaman web, meliputi tata letak, warna, tipografi, dan responsivitas tampilan pada berbagai ukuran layar. JavaScript merupakan bahasa pemrograman "), r("client-side", { italic: true }),
        r(" yang digunakan untuk menambahkan interaktivitas pada halaman web, seperti validasi formulir, animasi, dan manipulasi elemen halaman secara dinamis tanpa perlu memuat ulang halaman.")
      ]),
      body(
        "Ketiga teknologi ini bekerja secara sinergis dalam pembangunan antarmuka pengguna sistem e-katalog. HTML membentuk kerangka konten, CSS menjadikan tampilan menarik dan responsif di berbagai perangkat, serta JavaScript meningkatkan pengalaman pengguna melalui interaktivitas yang lebih baik."
      ),
      empty(),

      h3("3.2.8 Bootstrap"),
      mixed([
        r("Bootstrap merupakan "), r("framework", { italic: true }), r(" CSS "), r("open-source", { italic: true }),
        r(" yang dikembangkan oleh Twitter dan banyak digunakan dalam pengembangan antarmuka web yang responsif dan modern. Bootstrap menyediakan komponen-komponen UI yang siap pakai seperti navigasi, tombol, formulir, kartu, dan sistem grid yang memudahkan pengembang dalam membangun tampilan web yang konsisten dan kompatibel dengan berbagai ukuran layar perangkat ("), r("mobile-first", { italic: true }), r(").")
      ]),
      body(
        "Penggunaan Bootstrap dalam sistem e-katalog ini memungkinkan tampilan katalog produk yang dihasilkan menjadi responsif dan dapat diakses dengan baik baik melalui perangkat komputer desktop maupun perangkat mobile, sehingga memperluas aksesibilitas sistem kepada pengguna dari berbagai jenis perangkat."
      ),
      empty(),

      h3("3.2.9 Git (Version Control System)"),
      mixed([
        r("Git merupakan sistem kontrol versi ("), r("version control system", { italic: true }), r(") terdistribusi yang digunakan untuk melacak perubahan kode sumber selama proses pengembangan perangkat lunak. Git memungkinkan pengembang untuk menyimpan riwayat perubahan kode, membuat cabang ("), r("branch", { italic: true }), r(") untuk pengembangan fitur baru secara terisolasi, serta menggabungkan ("), r("merge", { italic: true }), r(") perubahan dari berbagai cabang secara terstruktur.")
      ]),
      body(
        "Dalam pelaksanaan Kerja Praktek ini, Git digunakan sebagai alat untuk mengelola versi kode secara terstruktur selama proses pengembangan sistem e-katalog yang dilakukan secara remote. Git memastikan seluruh perubahan kode terdokumentasi dengan baik dan dapat dikembalikan ke versi sebelumnya apabila terjadi kesalahan."
      ),
      empty(),

      h3("3.2.10 Metode Waterfall"),
      body(
        "Metode Waterfall merupakan salah satu model pengembangan perangkat lunak yang paling awal dan paling banyak digunakan dalam rekayasa perangkat lunak. Model ini menggambarkan pendekatan pengembangan yang bersifat sekuensial dan linier, di mana setiap tahapan harus diselesaikan secara tuntas sebelum tahapan berikutnya dapat dimulai, menyerupai aliran air terjun yang mengalir dari atas ke bawah."
      ),
      body("Tahapan-tahapan dalam metode Waterfall adalah sebagai berikut:", { noIndent: true }),
      numbered("Analisis Kebutuhan (Requirements Analysis): Pada tahap ini dilakukan identifikasi dan pendokumentasian seluruh kebutuhan fungsional dan non-fungsional sistem secara menyeluruh berdasarkan hasil observasi dan wawancara dengan pihak perusahaan."),
      numbered("Perancangan Sistem (System Design): Tahap ini mencakup perancangan arsitektur sistem, desain basis data, perancangan antarmuka pengguna, serta pemodelan alur sistem menggunakan diagram UML."),
      numbered("Implementasi (Implementation): Penulisan kode program dilakukan sesuai dengan hasil perancangan menggunakan framework Laravel, PHP, dan basis data MySQL."),
      numbered("Pengujian (Testing): Dilakukan pengujian menyeluruh terhadap seluruh fungsionalitas sistem untuk memastikan sistem berjalan sesuai dengan kebutuhan yang telah ditetapkan."),
      numbered("Pemeliharaan (Maintenance): Tahap akhir berupa perbaikan bug, penyesuaian sistem berdasarkan masukan pengguna, serta pengembangan fitur tambahan apabila diperlukan."),
      empty(),
      body(
        "Metode Waterfall dipilih dalam Kerja Praktek ini karena kebutuhan sistem yang telah terdefinisi dengan jelas sejak awal, alur pengembangan yang sistematis dan terdokumentasi dengan baik, serta kemudahannya dalam mengatur tahapan pengerjaan dalam konteks kerja tim yang terbatas waktu."
      ),
      empty(),

      h3("3.2.11 UML (Unified Modeling Language)"),
      mixed([
        r("UML ("), r("Unified Modeling Language", { italic: true }), r(") merupakan bahasa pemodelan standar yang digunakan dalam rekayasa perangkat lunak untuk memvisualisasikan, menspesifikasikan, membangun, dan mendokumentasikan artefak suatu sistem perangkat lunak. UML menyediakan berbagai jenis diagram yang dapat digunakan untuk merepresentasikan berbagai aspek sistem dari sudut pandang yang berbeda-beda.")
      ]),
      body("Jenis-jenis diagram UML yang digunakan dalam perancangan sistem e-katalog ini meliputi:", { noIndent: true }),
      bullet([r("Activity Diagram", { bold: true }), r(", digunakan untuk memodelkan alur aktivitas atau proses bisnis yang terjadi dalam sistem, baik pada kondisi sistem manual yang sedang berjalan maupun pada sistem yang diusulkan.")]),
      bullet([r("Use Case Diagram", { bold: true }), r(", digunakan untuk menggambarkan interaksi antara pengguna (aktor) dengan sistem, serta fungsionalitas-fungsionalitas yang disediakan oleh sistem.")]),
      bullet([r("Entity Relationship Diagram (ERD)", { bold: true }), r(", digunakan untuk memodelkan struktur basis data sistem, menggambarkan entitas-entitas yang terlibat beserta relasi dan atribut-atributnya.")]),
      empty(),

      // ══════════════════════════════════════
      // 3.3 PROSEDUR KERJA PRAKTEK
      // ══════════════════════════════════════
      h2("3.3 Prosedur Kerja Praktek"),
      empty(),
      body(
        "Prosedur Kerja Praktek menjelaskan tahapan-tahapan yang ditempuh selama pelaksanaan Kerja Praktek di PT. Intect Teknologi Indonesia. Mengacu pada metode pengembangan Waterfall yang telah diuraikan pada sub-bab sebelumnya, prosedur pelaksanaan Kerja Praktek ini terdiri dari beberapa tahapan yang dilaksanakan secara berurutan dan sistematis."
      ),
      empty(),

      // ─── 3.3.1 Analisis Kebutuhan ───
      h3("3.3.1 Analisis Kebutuhan Sistem"),
      empty(),
      body(
        "Tahap analisis kebutuhan merupakan langkah pertama yang dilakukan untuk memahami kondisi perusahaan dan kebutuhan sistem yang akan dibangun. Pada tahap ini dilakukan pengumpulan data melalui beberapa metode sebagai berikut:"
      ),
      empty(),

      h3("a. Observasi"),
      body(
        "Observasi dilakukan dengan mengamati kondisi dan kebutuhan PT. Intect Teknologi Indonesia secara tidak langsung (remote), mengingat pelaksanaan Kerja Praktek dilakukan tanpa kehadiran fisik di kantor perusahaan. Observasi difokuskan pada identifikasi kondisi digital perusahaan saat ini, termasuk ketiadaan sistem informasi produk berbasis web dan kebutuhan pengelolaan data produk yang masih dilakukan secara manual."
      ),
      empty(),

      h3("b. Wawancara"),
      body(
        "Wawancara dilakukan kepada pihak manajemen PT. Intect Teknologi Indonesia, khususnya Divisi Marketing, untuk memperoleh informasi mengenai jenis produk yang akan ditampilkan, fitur-fitur yang dibutuhkan dalam katalog digital, serta alur komunikasi dengan calon pelanggan yang diinginkan oleh perusahaan."
      ),
      empty(),

      h3("c. Studi Pustaka"),
      body(
        "Studi pustaka dilakukan dengan mengkaji jurnal ilmiah, buku referensi, dan dokumentasi teknis yang berkaitan dengan pengembangan sistem informasi berbasis web, framework Laravel, dan metode Waterfall, sebagaimana telah diuraikan pada sub-bab Tinjauan Pustaka dan Landasan Teori."
      ),
      empty(),

      h3("d. Kebutuhan Fungsional Sistem"),
      body("Berdasarkan hasil analisis kebutuhan, sistem e-katalog yang dibangun harus memenuhi kebutuhan fungsional sebagai berikut:", { noIndent: true }),
      bullet("Sistem dapat menampilkan daftar produk lengkap beserta foto, nama, deskripsi, spesifikasi teknis, dan informasi ketersediaan stok kepada pengunjung tanpa perlu melakukan login atau registrasi."),
      bullet("Sistem menyediakan fitur pencarian dan/atau filter produk berdasarkan kategori untuk memudahkan pengunjung menemukan produk yang diinginkan."),
      bullet("Sistem menyediakan fitur manajemen produk bagi administrator, meliputi penambahan, pengubahan, dan penghapusan data produk, kategori, gambar, serta informasi stok melalui panel administrasi yang dilindungi autentikasi login."),
      bullet("Sistem menampilkan informasi kontak perusahaan yang dapat digunakan oleh calon pelanggan untuk menghubungi perusahaan guna melakukan pemesanan produk."),
      empty(),

      h3("e. Kebutuhan Non-Fungsional Sistem"),
      bullet([r("Keamanan", { bold: true }), r(": Sistem panel administrasi hanya dapat diakses oleh pengguna yang telah terautentikasi melalui mekanisme login yang aman.")]),
      bullet([r("Responsivitas", { bold: true }), r(": Tampilan sistem dapat menyesuaikan diri dengan berbagai ukuran layar perangkat, baik desktop maupun mobile.")]),
      bullet([r("Ketersediaan", { bold: true }), r(": Sistem dapat diakses kapan saja melalui jaringan internet dengan tingkat ketersediaan yang tinggi.")]),
      bullet([r("Kemudahan Pemeliharaan", { bold: true }), r(": Kode sistem dibangun dengan struktur yang terorganisir menggunakan arsitektur MVC Laravel sehingga mudah untuk dipelihara dan dikembangkan lebih lanjut.")]),
      empty(),

      // ─── 3.3.2 Perancangan Sistem ───
      h3("3.3.2 Perancangan Sistem"),
      empty(),
      body(
        "Berdasarkan hasil analisis kebutuhan yang telah dilakukan, tahap selanjutnya adalah perancangan sistem. Perancangan sistem mencakup pemodelan proses bisnis yang sedang berjalan, pemodelan sistem yang diusulkan, serta perancangan basis data. Seluruh pemodelan sistem dilakukan menggunakan notasi UML (Unified Modeling Language)."
      ),
      empty(),

      h3("a. Activity Diagram Sistem yang Sedang Berjalan"),
      body(
        "Activity diagram sistem yang sedang berjalan menggambarkan alur proses bisnis PT. Intect Teknologi Indonesia sebelum sistem e-katalog dibangun. Karena perusahaan belum memiliki sistem informasi digital sebelumnya, proses pengenalan produk kepada calon pelanggan masih dilakukan secara manual. Alur proses yang sedang berjalan adalah sebagai berikut:"
      ),
      empty(),
      body("Calon pelanggan yang ingin mengetahui informasi produk PT. Intect Teknologi Indonesia harus menghubungi perusahaan secara langsung melalui telepon, email, atau media sosial. Petugas pemasaran kemudian memberikan informasi produk secara verbal atau melalui dokumen katalog fisik yang dikirimkan kepada calon pelanggan. Proses ini membutuhkan waktu yang relatif lama, bergantung pada ketersediaan petugas, dan tidak dapat diakses secara mandiri oleh calon pelanggan di luar jam kerja.", { noIndent: true }),
      empty(),
      placeholder("Activity Diagram Sistem yang Sedang Berjalan — akan dimasukkan sendiri"),
      caption("Gambar 3.1 Activity Diagram Sistem yang Sedang Berjalan"),
      empty(),

      h3("b. Activity Diagram Sistem yang Diusulkan"),
      body(
        "Activity diagram sistem yang diusulkan menggambarkan alur proses yang akan terjadi setelah sistem e-katalog dibangun dan diimplementasikan. Terdapat dua alur utama dalam sistem yang diusulkan, yaitu alur pengunjung (calon pelanggan) dan alur administrator."
      ),
      empty(),
      placeholder("Activity Diagram Sistem yang Diusulkan — akan dimasukkan sendiri"),
      caption("Gambar 3.2 Activity Diagram Sistem yang Diusulkan"),
      empty(),

      h3("c. Use Case Diagram"),
      body(
        "Use case diagram menggambarkan interaksi antara pengguna dengan sistem e-katalog. Terdapat dua aktor utama dalam sistem ini, yaitu Pengunjung (calon pelanggan) yang dapat mengakses katalog produk secara bebas, dan Administrator yang dapat mengelola seluruh data produk melalui panel administrasi setelah melalui proses autentikasi."
      ),
      empty(),
      placeholder("Use Case Diagram — akan dimasukkan sendiri"),
      caption("Gambar 3.3 Use Case Diagram Sistem E-Katalog"),
      empty(),

      h3("d. Entity Relationship Diagram (ERD)"),
      body(
        "Entity Relationship Diagram (ERD) menggambarkan struktur basis data sistem e-katalog secara konseptual. ERD memperlihatkan entitas-entitas yang terlibat dalam sistem beserta atribut dan relasi antar entitas tersebut."
      ),
      empty(),
      placeholder("Entity Relationship Diagram (ERD) — akan dimasukkan sendiri"),
      caption("Gambar 3.4 Entity Relationship Diagram (ERD) Sistem E-Katalog"),
      empty(),

      h3("e. Perancangan Basis Data"),
      body("Berdasarkan ERD yang telah dirancang, struktur tabel basis data sistem e-katalog adalah sebagai berikut:", { noIndent: true }),
      empty(),

      // Tabel users
      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [360, 2520, 1800, 1080, 2880],
        rows: [
          new TableRow({ children: [tc("No.", true, 360, AlignmentType.CENTER), tc("Nama Field", true, 2520), tc("Tipe Data", true, 1800), tc("Panjang", true, 1080), tc("Keterangan", true, 2880)] }),
          new TableRow({ children: [tc("1", false, 360, AlignmentType.CENTER), tc("id", false, 2520), tc("INT", false, 1800), tc("11", false, 1080), tc("Primary Key, Auto Increment", false, 2880)] }),
          new TableRow({ children: [tc("2", false, 360, AlignmentType.CENTER), tc("name", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("Nama administrator", false, 2880)] }),
          new TableRow({ children: [tc("3", false, 360, AlignmentType.CENTER), tc("email", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("Email login administrator", false, 2880)] }),
          new TableRow({ children: [tc("4", false, 360, AlignmentType.CENTER), tc("password", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("Password terenkripsi", false, 2880)] }),
          new TableRow({ children: [tc("5", false, 360, AlignmentType.CENTER), tc("created_at", false, 2520), tc("TIMESTAMP", false, 1800), tc("-", false, 1080), tc("Waktu data dibuat", false, 2880)] }),
        ]
      }),
      caption("Tabel 3.2 Struktur Tabel users"),
      empty(),

      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [360, 2520, 1800, 1080, 2880],
        rows: [
          new TableRow({ children: [tc("No.", true, 360, AlignmentType.CENTER), tc("Nama Field", true, 2520), tc("Tipe Data", true, 1800), tc("Panjang", true, 1080), tc("Keterangan", true, 2880)] }),
          new TableRow({ children: [tc("1", false, 360, AlignmentType.CENTER), tc("id", false, 2520), tc("INT", false, 1800), tc("11", false, 1080), tc("Primary Key, Auto Increment", false, 2880)] }),
          new TableRow({ children: [tc("2", false, 360, AlignmentType.CENTER), tc("nama_kategori", false, 2520), tc("VARCHAR", false, 1800), tc("100", false, 1080), tc("Nama kategori produk", false, 2880)] }),
          new TableRow({ children: [tc("3", false, 360, AlignmentType.CENTER), tc("slug", false, 2520), tc("VARCHAR", false, 1800), tc("100", false, 1080), tc("URL-friendly kategori", false, 2880)] }),
          new TableRow({ children: [tc("4", false, 360, AlignmentType.CENTER), tc("created_at", false, 2520), tc("TIMESTAMP", false, 1800), tc("-", false, 1080), tc("Waktu data dibuat", false, 2880)] }),
        ]
      }),
      caption("Tabel 3.3 Struktur Tabel kategoris"),
      empty(),

      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [360, 2520, 1800, 1080, 2880],
        rows: [
          new TableRow({ children: [tc("No.", true, 360, AlignmentType.CENTER), tc("Nama Field", true, 2520), tc("Tipe Data", true, 1800), tc("Panjang", true, 1080), tc("Keterangan", true, 2880)] }),
          new TableRow({ children: [tc("1", false, 360, AlignmentType.CENTER), tc("id", false, 2520), tc("INT", false, 1800), tc("11", false, 1080), tc("Primary Key, Auto Increment", false, 2880)] }),
          new TableRow({ children: [tc("2", false, 360, AlignmentType.CENTER), tc("kategori_id", false, 2520), tc("INT", false, 1800), tc("11", false, 1080), tc("Foreign Key ke tabel kategoris", false, 2880)] }),
          new TableRow({ children: [tc("3", false, 360, AlignmentType.CENTER), tc("nama_produk", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("Nama produk", false, 2880)] }),
          new TableRow({ children: [tc("4", false, 360, AlignmentType.CENTER), tc("slug", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("URL-friendly produk", false, 2880)] }),
          new TableRow({ children: [tc("5", false, 360, AlignmentType.CENTER), tc("deskripsi", false, 2520), tc("TEXT", false, 1800), tc("-", false, 1080), tc("Deskripsi lengkap produk", false, 2880)] }),
          new TableRow({ children: [tc("6", false, 360, AlignmentType.CENTER), tc("spesifikasi", false, 2520), tc("TEXT", false, 1800), tc("-", false, 1080), tc("Spesifikasi teknis produk", false, 2880)] }),
          new TableRow({ children: [tc("7", false, 360, AlignmentType.CENTER), tc("gambar", false, 2520), tc("VARCHAR", false, 1800), tc("255", false, 1080), tc("Path file gambar produk", false, 2880)] }),
          new TableRow({ children: [tc("8", false, 360, AlignmentType.CENTER), tc("stok", false, 2520), tc("ENUM", false, 1800), tc("-", false, 1080), tc("Tersedia / Tidak Tersedia", false, 2880)] }),
          new TableRow({ children: [tc("9", false, 360, AlignmentType.CENTER), tc("created_at", false, 2520), tc("TIMESTAMP", false, 1800), tc("-", false, 1080), tc("Waktu data dibuat", false, 2880)] }),
          new TableRow({ children: [tc("10", false, 360, AlignmentType.CENTER), tc("updated_at", false, 2520), tc("TIMESTAMP", false, 1800), tc("-", false, 1080), tc("Waktu data terakhir diperbarui", false, 2880)] }),
        ]
      }),
      caption("Tabel 3.4 Struktur Tabel produks"),
      empty(),

      // ─── 3.3.3 Implementasi ───
      h3("3.3.3 Implementasi Sistem"),
      empty(),
      body(
        "Tahap implementasi merupakan tahap penulisan kode program berdasarkan hasil perancangan yang telah dilakukan. Implementasi sistem e-katalog PT. Intect Teknologi Indonesia dilakukan dengan menggunakan tumpukan teknologi sebagai berikut:"
      ),
      empty(),

      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [2880, 5760],
        rows: [
          new TableRow({ children: [tc("Komponen", true, 2880), tc("Teknologi yang Digunakan", true, 5760)] }),
          new TableRow({ children: [tc("Back-End Framework", false, 2880), tc("Laravel (PHP)", false, 5760)] }),
          new TableRow({ children: [tc("Bahasa Pemrograman", false, 2880), tc("PHP 8.x", false, 5760)] }),
          new TableRow({ children: [tc("Basis Data", false, 2880), tc("MySQL", false, 5760)] }),
          new TableRow({ children: [tc("Front-End", false, 2880), tc("HTML, CSS, JavaScript, Bootstrap", false, 5760)] }),
          new TableRow({ children: [tc("Web Server (Lokal)", false, 2880), tc("XAMPP (Apache)", false, 5760)] }),
          new TableRow({ children: [tc("Code Editor", false, 2880), tc("Visual Studio Code", false, 5760)] }),
          new TableRow({ children: [tc("Version Control", false, 2880), tc("Git", false, 5760)] }),
          new TableRow({ children: [tc("Package Manager", false, 2880), tc("Composer (PHP), npm (Node.js)", false, 5760)] }),
        ]
      }),
      caption("Tabel 3.5 Teknologi yang Digunakan dalam Implementasi"),
      empty(),
      body(
        "Proses implementasi dilakukan secara remote oleh tim pengembang dan mencakup pembangunan dua modul utama, yaitu modul katalog publik yang dapat diakses oleh seluruh pengunjung tanpa registrasi, serta modul panel administrasi yang hanya dapat diakses oleh administrator yang telah terautentikasi."
      ),
      empty(),

      // ─── 3.3.4 Pengujian ───
      h3("3.3.4 Pengujian Sistem"),
      empty(),
      body(
        "Tahap pengujian dilakukan untuk memastikan bahwa seluruh fungsionalitas sistem berjalan sesuai dengan kebutuhan yang telah ditetapkan pada tahap analisis. Metode pengujian yang digunakan adalah pengujian Black Box Testing, yaitu pengujian yang berfokus pada fungsionalitas sistem dari sudut pandang pengguna tanpa melihat kode internal sistem."
      ),
      body(
        "Pengujian Black Box dilakukan dengan menguji setiap fitur sistem berdasarkan skenario penggunaan nyata, kemudian membandingkan hasil aktual dengan hasil yang diharapkan. Pengujian mencakup seluruh fungsionalitas sistem, meliputi tampilan halaman katalog publik, fungsi pencarian dan filter produk, proses login dan logout administrator, serta operasi CRUD (Create, Read, Update, Delete) data produk dan kategori pada panel administrasi."
      ),
      empty(),

      new Table({
        width: { size: 8640, type: WidthType.DXA },
        columnWidths: [360, 2880, 2160, 2160, 1080],
        rows: [
          new TableRow({ children: [tc("No.", true, 360, AlignmentType.CENTER), tc("Skenario Pengujian", true, 2880), tc("Hasil yang Diharapkan", true, 2160), tc("Hasil Aktual", true, 2160), tc("Status", true, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("1", false, 360, AlignmentType.CENTER), tc("Pengunjung membuka halaman katalog", false, 2880), tc("Daftar produk ditampilkan dengan lengkap", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("2", false, 360, AlignmentType.CENTER), tc("Pengunjung melihat detail produk", false, 2880), tc("Informasi detail produk ditampilkan dengan benar", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("3", false, 360, AlignmentType.CENTER), tc("Admin login dengan kredensial benar", false, 2880), tc("Admin berhasil masuk ke panel administrasi", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("4", false, 360, AlignmentType.CENTER), tc("Admin login dengan kredensial salah", false, 2880), tc("Sistem menampilkan pesan kesalahan", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("5", false, 360, AlignmentType.CENTER), tc("Admin menambahkan produk baru", false, 2880), tc("Produk baru tersimpan dan tampil di katalog", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("6", false, 360, AlignmentType.CENTER), tc("Admin mengubah data produk", false, 2880), tc("Data produk berhasil diperbarui", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
          new TableRow({ children: [tc("7", false, 360, AlignmentType.CENTER), tc("Admin menghapus produk", false, 2880), tc("Produk terhapus dari katalog dan basis data", false, 2160), tc("[ diisi saat pengujian ]", false, 2160), tc("[ ]", false, 1080, AlignmentType.CENTER)] }),
        ]
      }),
      caption("Tabel 3.6 Rencana Pengujian Black Box Testing"),
      empty(),

      // ─── 3.3.5 Pemeliharaan ───
      h3("3.3.5 Pemeliharaan Sistem"),
      empty(),
      body(
        "Tahap pemeliharaan merupakan tahap akhir dalam siklus pengembangan sistem dengan metode Waterfall. Pada tahap ini dilakukan perbaikan terhadap bug atau kesalahan yang ditemukan pasca pengujian, penyesuaian sistem berdasarkan masukan dari pihak PT. Intect Teknologi Indonesia, serta dokumentasi teknis sistem untuk memudahkan pengembangan lebih lanjut di masa mendatang."
      ),
      body(
        "Seluruh kode sumber sistem dikelola menggunakan Git sehingga setiap perubahan yang dilakukan pada tahap pemeliharaan terdokumentasi dengan baik dan dapat dilacak apabila diperlukan. Dokumentasi teknis yang disusun mencakup panduan instalasi sistem, penjelasan struktur kode, dan panduan penggunaan panel administrasi bagi administrator perusahaan."
      ),
      empty(),

    ]
  }]
});

Packer.toBuffer(doc).then(buffer => {
  fs.writeFileSync("/mnt/user-data/outputs/BAB_III_Metodologi_Penelitian_PT_Intect.docx", buffer);
  console.log("DONE");
}).catch(err => { console.error("Error:", err); });