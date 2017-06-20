package iii_conventions

data class MyDate (val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate = addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.number)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun contains(date: MyDate): Boolean = start < date && date < endInclusive
    override fun iterator(): Iterator<MyDate> = DateRangeIterator(this)
}

class DateRangeIterator (val dateRange: DateRange): Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}