package moseohcorp.server.domain.dns.exception

import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode

class BindNotFoundException : ApiException(ErrorCode.BIND_NOT_FOUND)
