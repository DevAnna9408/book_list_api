package kr.co.book.list.lib.crypto

interface CryptoService {
    fun encrypt(plainText: String?): String?

    fun decrypt(ciphertext: String?): String?

}
