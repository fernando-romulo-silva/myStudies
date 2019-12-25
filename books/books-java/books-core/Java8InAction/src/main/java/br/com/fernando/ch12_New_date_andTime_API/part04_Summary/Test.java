package br.com.fernando.ch12_New_date_andTime_API.part04_Summary;

// Summary
public class Test {

    //  The old java.util.Date class and all other classes used to model date and time in Java before Java 8
    // have many inconsistencies and design flaws, including their mutability and some poorly chosen offsets,
    // defaults, and naming.
    //
    //  The date-time objects of the new Date and Time API are all immutable.
    //
    //  This new API provides two different time representations to manage the different needs of humans
    // and machines when operating on it.
    //
    //  You can manipulate date and time objects in both an absolute and relative manner, and the result of
    // these manipulations is always a new instance, leaving the original one unchanged.
    //
    //  TemporalAdjusters allow you to manipulate a date in a more complex way than just changing one of
    // its values, and you can define and use your own custom date transformations.
    //
    //  You can define a formatter to both print and parse date-time objects in a specific format. These
    // formatters can be created from a pattern or programmatically and they’re all thread-safe.
    //
    //  You can represent a time zone, both relative to a specific region/location and as a fixed offset from
    // UTC/Greenwich, and apply it to a date-time object in order to localize it.
}
