package newMobileContacts;

public class Contact {
    private String name;
    private String phone;
    private String mobile;
    private String email;

    public Contact(String name, String phone, String mobile, String email) {
        this.name = name;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;

    }
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "; " + phone + "; " + mobile + "; " + email;
    }
}
