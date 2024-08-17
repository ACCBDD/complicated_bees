package com.accbdd.complicated_bees.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public class ComplicatedBeesCodecs {
    //hex string parser (no alpha)
    public static final Codec<Integer> HEX_STRING_CODEC = Codec.STRING.comapFlatMap(
            str -> {
                try {
                    return DataResult.success(Integer.valueOf(str, 16));
                } catch (NumberFormatException e) {
                    return DataResult.error(() -> str + " is not hexadecimal.");
                }
            },
            Integer::toHexString
    );
}
