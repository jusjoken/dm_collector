package ca.admin.delivermore.collector.data.reportitem;

public class CustomerTasks {
    private String email = "";
    private String name = "";
    private Long count = 0L;

    private String nameBlank = "-N/A";

    public CustomerTasks() {
    }

    public CustomerTasks(String email, String name, Long count) {
        this.email = email;
        this.name = name;
        this.count = count;
    }

    public String getEmail() {
        if(email==null || email.isEmpty()){
            return nameBlank;
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        if(email==null || email.isEmpty()){
            return nameBlank;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
