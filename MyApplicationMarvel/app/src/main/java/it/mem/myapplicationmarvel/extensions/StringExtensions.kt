package it.mem.myapplicationmarvel.extensions

fun String.md5():String{
    val digest=java.security.MessageDigest.getInstance("MD5")
    digest.update(toByteArray())
    val messageDigest=digest.digest()
    val hexString=StringBuilder()
    for (aMessageDigest in messageDigest){
        var h=Integer.toHexString(0xFF and aMessageDigest.toInt())
        while (h.length<2)
            h="0$h"
        hexString.append(h)
    }
    return hexString.toString()
}