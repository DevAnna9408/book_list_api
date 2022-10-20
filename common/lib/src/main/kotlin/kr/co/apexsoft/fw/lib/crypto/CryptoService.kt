package kr.co.apexsoft.fw.lib.crypto

interface CryptoService {
    fun encrypt(plainText: String?): String?

    fun decrypt(ciphertext: String?): String?

}
