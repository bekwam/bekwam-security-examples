package us.bekwam.security.examples.codecs;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonsCodecMD5Hex implements MD5Hex {

    @Override
    public String digest(String inbytes) {
        return new DigestUtils("MD5").digestAsHex(inbytes);
    }
}
