package kr.co.book.list.lib.aws.kms.exception

import kr.co.book.list.lib.utils.MessageUtil

class KMSEncryptException(error: String?)
    : RuntimeException(MessageUtil.getMessage("KMS_FAIL") + "/" + error)  {
}
