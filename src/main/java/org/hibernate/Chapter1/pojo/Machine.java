package org.hibernate.Chapter1.pojo;

import jakarta.persistence.*;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int machineId;
    String machineName;
    String machineType;

    @OneToOne
    @JoinColumn(name = "student_id") // owning side
    private Student student;

    public Machine() {
    }

    public Machine(String machineName, String machineType) {
        this.machineName = machineName;
        this.machineType = machineType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineId=" + machineId +
                ", machineName='" + machineName + '\'' +
                ", machineType='" + machineType + '\'' +
                '}';
    }
}
