package com.pachira.top.domain;

import java.text.DecimalFormat;

/**
 * @author 王添宝
 * @date 2021-09-12 16:49
 **/
public class TjTop {
    private Integer pid;
    private double tot_virt;
    private double tot_res;
    private double tot_shr;
    private double tot_cpu;
    private double tot_mem;

    private double min_virt = Double.MAX_VALUE;
    private double min_res = Double.MAX_VALUE;
    private double min_shr = Double.MAX_VALUE;
    private double min_cpu = Double.MAX_VALUE;
    private double min_mem = Double.MAX_VALUE;

    private double max_virt;
    private double max_res;
    private double max_shr;
    private double max_cpu;
    private double max_mem;

    private double count;
    private String command;

    public void addTop(Top top) {
        double virt = toVal(top.getVirt());
        double res = toVal(top.getRes());
        double shr = toVal(top.getShr());
        double cpu = Double.parseDouble(top.getCpu());
        double mem = Double.parseDouble(top.getMem());

        count++;

        tot_virt += virt;
        tot_res += res;
        tot_shr += shr;
        tot_cpu += cpu;
        tot_mem += mem;

        min_virt = Math.min(min_virt, virt);
        min_res = Math.min(min_res, res);
        min_shr = Math.min(min_shr, shr);
        min_cpu = Math.min(min_cpu, cpu);
        min_mem = Math.min(min_mem, mem);

        max_virt = Math.max(max_virt, virt);
        max_res = Math.max(max_res, res);
        max_shr = Math.max(max_shr, shr);
        max_cpu = Math.max(max_cpu, cpu);
        max_mem = Math.max(max_mem, mem);

        command = top.getCommand();
        pid = top.getPid();

    }

    private double toVal(String yz) {
        yz = yz.toLowerCase();
        if (yz.endsWith("t")) {
            return (double)(Double.parseDouble(yz.substring(0, yz.length() - 1)) * 1024L * 1024L * 1024L * 1024L);
        } else if (yz.endsWith("g")) {
            return (double)(Double.parseDouble(yz.substring(0, yz.length() - 1)) * 1024L * 1024L * 1024L);
        } else if (yz.endsWith("m")) {
            return (double)(Double.parseDouble(yz.substring(0, yz.length() - 1)) * 1024L * 1024L);
        } else {
            return (double)(Double.parseDouble(yz.substring(0, yz.length() - 1)) * 1024L);
        }
    }

    private String formatVal(double val) {
        DecimalFormat df = new DecimalFormat("0.###");
        if (val < 1024) {
        	return val + "byte";
        }
        val = val / 1024;
        if (val / 1024 < 1) {
            return df.format(val) + "KB";
        }
        val = val / 1024;
        if (val / 1024 < 1) {
            return df.format(val) + "MB";
        }
        val = val / 1024;
        if (val / 1024 < 1) {
            return df.format(val) + "GB";
        }
        val = val / 1024;
        return df.format(val) + "TB";
    }

    public String toRes() {
        if (count == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.###");
        sb.append("平均值\t").append(formatVal(tot_virt / count)).append("\t")
                .append(formatVal(tot_res / count)).append("\t")
                .append(formatVal(tot_shr / count)).append("\t")
                .append(df.format(tot_cpu / count)).append("\t")
                .append(df.format(tot_mem / count)).append("\t")
                .append(pid).append("[").append(command).append("]\n");
        sb.append("最大值\t").append(formatVal(max_virt)).append("\t")
                .append(formatVal(max_res)).append("\t")
                .append(formatVal(max_shr)).append("\t")
                .append(df.format(max_cpu)).append("\t")
                .append(df.format(max_mem)).append("\t")
                .append(pid).append("[").append(command).append("]\n");
        sb.append("最小值\t").append(formatVal(min_virt)).append("\t")
                .append(formatVal(min_res)).append("\t")
                .append(formatVal(min_shr)).append("\t")
                .append(df.format(min_cpu)).append("\t")
                .append(df.format(min_mem)).append("\t")
                .append(pid).append("[").append(command).append("]");
        return sb.toString();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public double getTot_virt() {
        return tot_virt;
    }

    public void setTot_virt(double tot_virt) {
        this.tot_virt = tot_virt;
    }

    public double getTot_res() {
        return tot_res;
    }

    public void setTot_res(double tot_res) {
        this.tot_res = tot_res;
    }

    public double getTot_shr() {
        return tot_shr;
    }

    public void setTot_shr(double tot_shr) {
        this.tot_shr = tot_shr;
    }

    public double getTot_cpu() {
        return tot_cpu;
    }

    public void setTot_cpu(double tot_cpu) {
        this.tot_cpu = tot_cpu;
    }

    public double getTot_mem() {
        return tot_mem;
    }

    public void setTot_mem(double tot_mem) {
        this.tot_mem = tot_mem;
    }

    public double getMin_virt() {
        return min_virt;
    }

    public void setMin_virt(double min_virt) {
        this.min_virt = min_virt;
    }

    public double getMin_res() {
        return min_res;
    }

    public void setMin_res(double min_res) {
        this.min_res = min_res;
    }

    public double getMin_shr() {
        return min_shr;
    }

    public void setMin_shr(double min_shr) {
        this.min_shr = min_shr;
    }

    public double getMin_cpu() {
        return min_cpu;
    }

    public void setMin_cpu(double min_cpu) {
        this.min_cpu = min_cpu;
    }

    public double getMin_mem() {
        return min_mem;
    }

    public void setMin_mem(double min_mem) {
        this.min_mem = min_mem;
    }

    public double getMax_virt() {
        return max_virt;
    }

    public void setMax_virt(double max_virt) {
        this.max_virt = max_virt;
    }

    public double getMax_res() {
        return max_res;
    }

    public void setMax_res(double max_res) {
        this.max_res = max_res;
    }

    public double getMax_shr() {
        return max_shr;
    }

    public void setMax_shr(double max_shr) {
        this.max_shr = max_shr;
    }

    public double getMax_cpu() {
        return max_cpu;
    }

    public void setMax_cpu(double max_cpu) {
        this.max_cpu = max_cpu;
    }

    public double getMax_mem() {
        return max_mem;
    }

    public void setMax_mem(double max_mem) {
        this.max_mem = max_mem;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
