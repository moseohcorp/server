package moseohcorp.server.domain.dns.exception

import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode

class DomainNotFoundException : ApiException(ErrorCode.DOMAIN_NOT_FOUND)