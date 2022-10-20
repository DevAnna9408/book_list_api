package kr.co.apexsoft.fw.lib.error

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class UnauthenticatedAccessException
    : RuntimeException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS")) {
}
