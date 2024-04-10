package moseohcorp.server.domain.dns.exception

import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode

class DNSNotFoundException : ApiException(ErrorCode.DNS_NOT_FOUND)