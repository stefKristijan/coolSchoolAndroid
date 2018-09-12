package hr.ferit.coolschool.model;


import java.io.Serializable;
import java.util.Set;

public class School implements Serializable{

    private Integer schoolId;
    private String name;
    private String address;
    private String city;
    private String state;
    private Integer postalCode;
    private SchoolType type;

    private Set<UserSchool> userSchools;

    public School(String name, String address, String city, String state, Integer postalCode, SchoolType type, Set<UserSchool> userSchools) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.type = type;
        this.userSchools = userSchools;
    }

    public School() {
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode=" + postalCode +
                ", type=" + type +
                ", userSchools=" + userSchools +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        if (schoolId != null ? !schoolId.equals(school.schoolId) : school.schoolId != null)
            return false;
        if (name != null ? !name.equals(school.name) : school.name != null) return false;
        if (address != null ? !address.equals(school.address) : school.address != null)
            return false;
        if (city != null ? !city.equals(school.city) : school.city != null) return false;
        if (state != null ? !state.equals(school.state) : school.state != null) return false;
        if (postalCode != null ? !postalCode.equals(school.postalCode) : school.postalCode != null)
            return false;
        return type == school.type;
    }

    @Override
    public int hashCode() {
        int result = schoolId != null ? schoolId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public SchoolType getType() {
        return type;
    }

    public void setType(SchoolType type) {
        this.type = type;
    }

    public Set<UserSchool> getUserSchools() {
        return userSchools;
    }

    public void setUserSchools(Set<UserSchool> userSchools) {
        this.userSchools = userSchools;
    }
}
