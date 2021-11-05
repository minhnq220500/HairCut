package com.example.haircut.model;

import java.util.Comparator;

public class ServiceCount implements Comparator<ServiceCount>{
    private String serviceID;
    private String serviceName;
    private int count;

    public ServiceCount(String serviceID, String serviceName, int count) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.count = count;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ServiceCount{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", count=" + count +
                '}';
    }

    //giảm dần
    @Override
    public int compare(ServiceCount serviceCount1, ServiceCount serviceCount2) {
        if(serviceCount1.getCount()<serviceCount2.getCount()){
            return -1;
        }else{
            if(serviceCount1.getCount()==serviceCount2.getCount()){
                return 0;
            }else{
                return 1;
            }
        }
    }
}
