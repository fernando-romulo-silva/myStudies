package org.nandao.ch05TheNewDateTime.part05ZonedTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

// Each time zone has an ID, such as America/New_York or Europe/Berlin. To find out all
// available time zones, call ZoneId.getAvailableIds. At the time of this writing, there
// were almost 600 IDs.
//
// Given a time zone ID, the static method ZoneId.of(id) yields a ZoneId object. You
// can use that object to turn a LocalDateTime object into a ZonedDateTime object by calling
// local.atZone(zoneId), or you can construct a ZonedDateTime by calling the static method
// ZonedDateTime.of(year, month, day, hour, minute, second, nano, zoneId)
public class Test {

    public static void main(final String[] args) {

        // When daylight savings time starts, clocks advance by an hour. What happens
        // when you construct a time that falls into the skipped hour? For example, in 2013,
        // Central Europe switched to daylight savings time on March 31 at 2:00. If you try
        // to construct nonexistent time March 31 2:30, you actually get 3:30.

        final ZonedDateTime apollo11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0, ZoneId.of("America/New_York"));
        // 1969-07-16T09:32-04:00[America/New_York]
        System.out.println(apollo11launch);

        final ZonedDateTime skipped = ZonedDateTime.of( //
                LocalDate.of(2013, 3, 31), //
                LocalTime.of(2, 30), //
                ZoneId.of("Europe/Berlin"));
        // Constructs March 31 3:30

        System.out.println(skipped);

        final ZonedDateTime ambiguous = ZonedDateTime.of(//
                LocalDate.of(2013, 10, 27), // End of daylight savings time
                LocalTime.of(2, 30), //
                ZoneId.of("Europe/Berlin"));
        // 2013-10-27T02:30+02:00[Europe/Berlin]

        final ZonedDateTime anHourLater = ambiguous.plusHours(1);
        // 2013-10-27T02:30+01:00[Europe/Berlin]
        System.out.println(anHourLater);

        //You also need to pay attention when adjusting a date across daylight savings
        //time boundaries. For example, if you set a meeting for next week, don’t add a
        // duration of seven days:
        final ZonedDateTime meeting = ZonedDateTime.of(//
                LocalDate.of(2013, 10, 27), // End of daylight savings time
                LocalTime.of(2, 30), //
                ZoneId.of("Europe/Berlin"));

        final ZonedDateTime nextMeetingWrong = meeting.plus(Duration.ofDays(7));
        // Caution! Won’t work with daylight savings time
        System.out.println(nextMeetingWrong);

        final ZonedDateTime nextMeetingRight = meeting.plus(Period.ofDays(7)); // OK
        System.out.println(nextMeetingRight);
    }
}
