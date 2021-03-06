package mobileapp.ctemplar.com.ctemplarapp.net.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePasswordRequest {

    @SerializedName("old_password")
    private String oldPassword;

    @SerializedName("password")
    private String password;

    @SerializedName("confirm_password")
    private String confirmPassword;

    @SerializedName("delete_data")
    private boolean deleteData;

    @SerializedName("new_keys")
    private List<MailboxKey> mailboxesKeys;

    public ChangePasswordRequest() {

    }

    public ChangePasswordRequest(String oldPassword, String password, String confirmPassword, Boolean deleteData) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.deleteData = deleteData;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isDeleteData() {
        return deleteData;
    }

    public void setDeleteData(boolean deleteData) {
        this.deleteData = deleteData;
    }

    public List<MailboxKey> getMailboxesKeys() {
        return mailboxesKeys;
    }

    public void setMailboxesKeys(List<MailboxKey> mailboxesKeys) {
        this.mailboxesKeys = mailboxesKeys;
    }
}
