package lab.contract.biz.user.controller.form;
public class UpdateUserForm {
    private String name;

    public UpdateUserForm() {
    }

    public UpdateUserForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
