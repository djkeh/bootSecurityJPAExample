package com.me.bootSecurityJPAExample.domain;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;


public enum EnumTest {
    TEST1("a"),
    TEST2("b"),
    TEST3("c"),
    TEST4("d"),
    TEST5("e"),
    TEST6("f"),
    TEST7("g"),
    TEST8("h");
    
    private final String code;

    private static final HashMap<String, EnumTest> HASH_MAP;
    private static final TreeMap<String, EnumTest> TREE_MAP;

    static {
        HASH_MAP = new HashMap<>();
        TREE_MAP = new TreeMap<>();
        for (EnumTest item : values()) {
            HASH_MAP.put(item.name(), item);
            TREE_MAP.put(item.name(), item);
        }
    }

    EnumTest(String code) {
        this.code = code;
    }

    public String getCode() { return code; }

    public static EnumTest getHashMapEnum(String str) { return HASH_MAP.get(str); }
    public static EnumTest getTreeMapEnum(String str) { return TREE_MAP.get(str); }
    public static Optional<EnumTest> getHashMapEnumByCode(String str) {
        return Stream.of(values())
                .filter(a -> a.code.equals(str))
                .findFirst();
    }
}
