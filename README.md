# 🍽️ Sistem Manajemen Restoran (Java CLI)

Sistem manajemen restoran berbasis **Command Line Interface (CLI)** yang dibangun menggunakan bahasa **Java**. 

Proyek ini menerapkan konsep **Object-Oriented Programming (OOP)** seperti *Inheritance*, *Polymorphism*, *Encapsulation*, dan *Abstraction*.

Sistem ini menggunakan **File I/O** (file `.txt`) sebagai database sederhana untuk menyimpan data.

---

## ✨ Fitur Utama

Sistem dibagi berdasarkan peran pengguna (*Role*):

### 👤 Customer
* **Registrasi & Login:** Membuat akun baru atau masuk dengan akun lama.
* **Lihat Menu:** Melihat daftar makanan dan minuman beserta harganya.
* **Buat Pesanan:** Memesan makanan/minuman dan memilih nomor meja.
* **Bayar Mandiri:** Melihat tagihan (*bill*) dan memilih metode pembayaran (Cash, Card, QRIS).

### 🧑‍🍳 Koki (Chef)
* **Lihat Pesanan Masuk:** Melihat pesanan yang statusnya "Dipesan".
* **Masak:** Mengubah status pesanan menjadi "Selesai Dimasak".

### 💁 Pelayan (Waiter)
* **Update Status:** Membantu memantau atau membatalkan pesanan jika diperlukan.

### 💸 Kasir
* **Proses Pembayaran:** Melihat pesanan yang siap dibayar dan memproses transaksi.
* **Cetak Struk:** Mencetak rincian transaksi ke layar setelah pembayaran berhasil.

---

## 🛠️ Teknologi yang Digunakan
* **Bahasa:** Java
* **Konsep:** OOP (Abstract Class, Interface, Overriding, Overloading).
* **Database:** Text Files (`.txt`).
* **Version Control:** Git.

---

## 📂 Struktur Data (.txt)
1. menu.txt Format: Tipe,Nama,Harga,Info1,Info2
2. pegawai.txt Format: ID,Nama,Password,Peran
3. customer.txt Format: ID,Nama,Password

🚀 Cara Menjalankan (How to Run)
1. Compile Program di folder induk: javac UASLabPBOB_3/*.java
2. Jalankan Program: java UASLabPBOB_3.RestaurantApp
