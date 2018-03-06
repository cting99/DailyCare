package cting.com.robin.support.teethcare.braces;

import cting.com.robin.support.teethcare.models.SliceItem;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class DateSlice extends SliceItem {
    public static final String pattern = TimeFormatHelper.DATA_FORMAT;

    @Override
    public String getDiff() {
        return String.valueOf(getDiffInNumeric());
    }

    @Override
    public long getDiffInNumeric() {  // day count
        return TimeFormatHelper.getDayCountByDate(from, to);
    }

    public static class Builder {
        private DateSlice dateSlice = new DateSlice();

        public DateSlice build() {
            return dateSlice;
        }

        public Builder from(String from) {
            dateSlice.from = from;
            return this;
        }

        public Builder to(String to) {
            dateSlice.to = to;
            return this;
        }

        public static DateSlice sampleData() {
            return new Builder()
                    .from("2018/03/01")
                    .to("2018/03/08")
                    .build();
        }

        public static DateSlice sampleData(int i) {
            /*try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(TimeFormatHelper.DATA_FORMAT);
                String baseDateStr = "2017/10/30";
                Date baseDate = dateFormat.parse(baseDateStr);
                long DURATION = TimeFormatHelper.ONE_HOUR * 24 * 8;
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            return new Builder()
                    .from("2018/03/01")
                    .to("2018/03/08")
                    .build();
        }
    }
}
