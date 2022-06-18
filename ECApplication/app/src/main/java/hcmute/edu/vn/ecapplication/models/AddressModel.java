package hcmute.edu.vn.ecapplication.models;

public class AddressModel {

    String userAddress;
    boolean isSelected;

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AddressModel() {
    }
}
