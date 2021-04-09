package com.me.bootSecurityJPAExample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.domain.EnumTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class LogicTest {

    private String timer(int repeat, List<String> testList, Consumer<String> c) {
        Random rand = new Random();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < repeat; i++) { c.accept(testList.get(rand.nextInt(testList.size()))); }
//        for (int i = 0; i < repeat; i++) { c.accept(testList.get(5)); }
        long delta = System.currentTimeMillis() - startTime;

        BigDecimal a = BigDecimal.ONE;
//        a = null + BigDecimal.ONE;

        return String.format("[%d] times\tresult:\t%d ms", repeat, delta);
    }

    class WeirdClass {
        public String text;

        public WeirdClass() {}

        public WeirdClass(String text) {
            this.text = text;

            Set<Integer> set = new HashSet<>();
            set.equals(null);
            set.contains(null);
            text.hashCode();
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private String weirdReturn(WeirdClass weirdClass) {
        return weirdClass.text = "Test";
    }

    @Test
    void weirdReturnTest() {
        // Given
        WeirdClass weirdClass = new WeirdClass();
        weirdClass.text = "Something";
        List<WeirdClass> weirdClasses = new ArrayList<>();
        weirdClasses.add(new WeirdClass("anything"));
        weirdClasses.add(new WeirdClass("anything"));

        // When
        String str = weirdReturn(weirdClass);
        List<String> list = weirdClasses
                .stream()
                .map(x -> { x.setText("Test"); return x.text; })
                .collect(Collectors.toList());

        // Then
        System.out.println(str);
        System.out.println(weirdClass.text);
        System.out.println(list);
    }

    @Test
    void UriComponentsBuilderTest() {
        String input = "https://www.this.is.com/test-purpose/url/with_weirdWordsAnd---?parameter1=123&parameter2=hahaha%20%EB%A8%B8%EC%A0%80%EB%A6%AC%EC%95%BC";
        String url1 = UriComponentsBuilder.fromHttpUrl(input).queryParam("additional", "param").toUriString();
        String url2 = UriComponentsBuilder.fromPath(input).queryParam("additional", "param").toUriString();

        System.out.println("fromHttpUrl: " + url1);
        System.out.println("fromPath:    " + url2);
    }

    @Test
    void printMapTest() {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        resultMap.put("a", 1);
        resultMap.put("b", 2);
        resultMap.put("c", 3);
        resultMap.get("s");
        String msg = "";

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            msg += "\n - " + entry.getKey() + ": " + entry.getValue() + '건';
        }
    }

    double fun(double money, int count) {
        if (count < 1) {
            return money;
        }
        return fun(money * 1.08, count - 1);
    }

    @Test
    void jang() {
        System.out.println(fun(200, 30));
    }

    @Test
    void ttt() throws Exception {
        ObjectMapper m = new ObjectMapper();
        BigDecimal a = BigDecimal.valueOf(100.0);
        System.out.println(a.toString());
        System.out.println(a.toPlainString());
        System.out.println(a.toBigInteger());
        System.out.println(a.toBigIntegerExact());
        System.out.println(m.writeValueAsString(a));
    }

    @Test
    void enumFindSpeedTest() throws Exception {
        // Given
        int times = 30000000;
        List<String> testList = Arrays.stream(EnumTest.values()).map(EnumTest::name).collect(Collectors.toList());

        // When
        String result1 = timer(times, testList, EnumTest::valueOf);
        String result2 = timer(times, testList, EnumTest::getHashMapEnum);
        String result3 = timer(times, testList, EnumTest::getTreeMapEnum);

        // Then
        System.out.println("valueOf() - " + result1);
        System.out.println("hashMap - " + result2);
        System.out.println("treeMap - " + result3);
    }

    @Test
    void test() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date d1 = format.parse("20180701");
        Date d2 = format.parse("20181001");

        long diff = d1.getTime() - d2.getTime();

        System.out.println("diff: " + diff);
        System.out.println("in days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        Integer a = 10;
        Integer b = 20;
        System.out.println(a.compareTo(b));

        LocalDate date1 = LocalDate.of(2018, 1, 1);
        LocalDate date2 = LocalDate.of(2018, 1, 8);
        System.out.println(date1.compareTo(date2));
    }

    @Test
    void durationTest() {
        LocalDate date1 = LocalDate.of(1992, 2, 2);
        LocalDate date2 = LocalDate.of(2010, 2, 1);

        System.out.println(ChronoUnit.DAYS.between(date1, date2));
        System.out.println(ChronoUnit.YEARS.between(date1, date2));
        System.out.println(ChronoUnit.DAYS.between(date2, date1));
        System.out.println(ChronoUnit.YEARS.between(date2, date1));

        LocalDateTime date3 = LocalDateTime.of(1992, 2, 1, 0, 0, 1);
        LocalDateTime date4 = LocalDateTime.of(2010, 2, 1, 0, 0, 0);

        System.out.println(ChronoUnit.DAYS.between(date3, date4));
        System.out.println(ChronoUnit.YEARS.between(date3, date4));
    }

    @Test
    void stringCompareToTest() {
        String s1 = "adfasf";
        String s2 = "waef34df";

        System.out.println(s1.compareTo(s2));
    }

    @Test
    void charToAsciiTest() {
        char c = '1';

        System.out.printf("%d\n", (int) c);
    }

    @Test
    void stringReplaceTest() {
        String str = "   123 4 5 6678   9 ";

        System.out.println("\"" + str + "\"");
        System.out.println("\"" + str.trim() + "\"");
        System.out.println("\"" + str.replace(" ", "") + "\"");
        System.out.println("\"" + str.replaceAll(" ", "") + "\"");
        System.out.println("\"" + str.trim().replaceAll(" ", "") + "\"");
    }

    @Test
    void stringReplacePerformanceTest() {
        long start = System.currentTimeMillis();
        String str = "abc";

        for (int i = 0; i < 10000000; i++) {
            str.replace(" ", "");
        }

        long end = System.currentTimeMillis();

        System.out.println("result: " + (end - start)/1000.0 + "s");
    }

    @Test
    void flatMapTest() {
        List<SSS> list = List.of(SSS.of(List.of("a", "b"), "Hi", 1), SSS.of(List.of("b"), "There", 2));
        List<List<String>> strs = List.of(List.of("a", "b"), List.of("a", "b"));

        list.stream().flatMap(x -> x.sth.stream()).forEach(System.out::print);
        System.out.println();
        list.stream().map(x -> x.sth).forEach(y -> y.forEach(System.out::print));
        System.out.println();

        String result = strs.stream().flatMap(Collection::stream).findFirst().orElse("empty");
        System.out.println(result);
        result = strs.stream().findFirst().orElse(List.of()).stream().findFirst().orElse("empty");
        System.out.println(result);

        Optional<Computer> computer = Optional.of(new Computer());
        computer.flatMap(x -> x.soundcard).flatMap(x -> x.usb);
        computer.map(x -> x.soundcard);
        computer.map(x -> x.two);
        String name = computer.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUsb)
                .map(USB::getVersion)
                .orElse("UNKNOWN");

        System.out.println(name);
    }

    @Test
    void instanceofTest() throws Exception {
        Throwable a = new Exception("root exception a");
        Throwable b = new InvalidDataAccessResourceUsageException("root exception b") {
        };

        Throwable a1 = new RuntimeException("nested runtime from a", a);
        Throwable a2 = new InvalidDataAccessResourceUsageException("nested runtime from a1", a1);
        Throwable a3 = new InvalidDataAccessResourceUsageException("nested runtime from a2", a2);
        Throwable a4 = new IncorrectUpdateSemanticsDataAccessException("nested runtime from a3", a3);

        System.out.println(a.getClass().isInstance(b));
        System.out.println(a1.getClass().isInstance(b));
        System.out.println(a2.getClass().isInstance(b));
        System.out.println(a3.getClass().isInstance(b));
        System.out.println(a4.getClass().isInstance(b));
        System.out.println();

        System.out.println(b.getClass().isInstance(a));
        System.out.println(b.getClass().isInstance(a1));
        System.out.println(b.getClass().isInstance(a2));
        System.out.println(b.getClass().isInstance(a3));
        System.out.println(b.getClass().isInstance(a4));
    }


    @AllArgsConstructor(staticName = "of")
    static class SSS {
        List<String> sth;
        String name;
        int age;
    }

    public class Computer {
        @Getter Optional<Soundcard> soundcard;
        @Getter Soundcard two;
        @Getter List<Soundcard> three;
    }

    public class Soundcard {
        @Getter Optional<USB> usb;
    }

    public class USB{
        String getVersion(){ return "a"; }
    }

}
