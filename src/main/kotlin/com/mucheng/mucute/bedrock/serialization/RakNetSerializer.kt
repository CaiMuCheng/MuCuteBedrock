package com.mucheng.mucute.bedrock.serialization

import kotlinx.io.Source

interface RakNetSerializer {

    fun toSource(): Source

}