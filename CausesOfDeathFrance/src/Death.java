public class Death {
    private int year;
    private String country;
    private String sex;
    private String ICD10;

    static final int YEAR_SIZE = 6;
    static final int COUNTRY_SIZE = 10;
    static final int SEX_SIZE = 10;
    static final int ICD10_SIZE = 130;


    public Death(int year, String country, String sex, String ICD10) {
        this.year = year;
        this.country = country;
        this.sex = sex;
        this.ICD10 = ICD10;
    }

    public Death() {}
    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getSex() {
        return sex;
    }

    public String getICD10() {
        return ICD10;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setICD(String ICD10) {
        this.ICD10 = ICD10;
    }


    public byte[] getSexInBytes() {
        byte[] itemBytes = new byte[SEX_SIZE];
        System.arraycopy(sex.getBytes(), 0, itemBytes, 0, sex.length());
        return itemBytes;
    }

    public byte[] getCountryInBytes() {
        byte[] itemBytes = new byte[COUNTRY_SIZE];
        System.arraycopy(country.getBytes(), 0, itemBytes, 0, country.length());
        return itemBytes;
    }

    public byte[] getICD10InBytes() {
        byte[] itemBytes = new byte[ICD10_SIZE];
        System.arraycopy(ICD10.getBytes(), 0, itemBytes, 0, ICD10.length());
        return itemBytes;
    }

    @Override
    public String toString() {
        return "Death{" +
                "year='" + year + '\'' +
                ", country='" + country + '\'' +
                ", sex='" + sex + '\'' +
                ", ICD10='" + ICD10 + '\'' +
                '}';
    }
}