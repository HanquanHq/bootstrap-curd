package cn.hanquan.bootstrapcurd.entities;

public class ExcelTable {
    private Integer id;
    private String firstCell;
    private String secondCell;
    private String thirdCell;
    private String fourthCell;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(String firstCell) {
        this.firstCell = firstCell;
    }

    public String getSecondCell() {
        return secondCell;
    }

    public void setSecondCell(String secondCell) {
        this.secondCell = secondCell;
    }

    public String getThirdCell() {
        return thirdCell;
    }

    public void setThirdCell(String thirdCell) {
        this.thirdCell = thirdCell;
    }

    public String getFourthCell() {
        return fourthCell;
    }

    public void setFourthCell(String fourthCell) {
        this.fourthCell = fourthCell;
    }

    @Override
    public String toString() {
        return "ExcelTable{" +
                "id=" + id +
                ", firstCell='" + firstCell + '\'' +
                ", secondCell='" + secondCell + '\'' +
                ", thirdCell='" + thirdCell + '\'' +
                ", fourthCell='" + fourthCell + '\'' +
                '}';
    }
}
