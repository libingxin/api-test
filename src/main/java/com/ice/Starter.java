package com.ice;

public abstract class Starter implements Runnable {
    protected final String[] args;

    public Starter(String[] args) {
        this.args = args;
    }

    public void run() {
        Parameter parameter = parse();
        if(parameter.isHelp()){
            printHelp();
            return;
        }

        init(parameter);
        innerRun(parameter);
        output(parameter.getOutput());
    }

    protected abstract Parameter parse();

    private void init(Parameter parameter) {
    }

    private void innerRun(Parameter parameter) {
    }

    private void output(String output) {
    }

    private void printHelp() {
        String message = "使用帮助: java -jar api-test.jar [options...]\n" +
                " -h, --help                     输出帮助信息\n" +
                " -t, --thread <value>           并发数\n" +
                " -c, --count <value>            调用次数\n" +
                " -s, --second <value>           调用时长：单位秒\n" +
                " -p, --property <key=value>     自定义扩展属性\n" +
                " -o, --output <file>            结果输出到文件中";
        System.out.println(message);
    }
}
