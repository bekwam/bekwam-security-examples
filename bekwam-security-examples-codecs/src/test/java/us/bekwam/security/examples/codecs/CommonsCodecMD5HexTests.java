package us.bekwam.security.examples.codecs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonsCodecMD5HexTests {

    private final CommonsCodecMD5Hex md5Hex =
            new CommonsCodecMD5Hex();

    @Test
    public void hashPassword() {
        assertEquals(
                "72929ca32a06022b1e01fe5e048dbe69",
                md5Hex.digest("carlw2:ApplicationRealm2:t6ru45BA")
        );
    }
}
