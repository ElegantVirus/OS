package os;


import VirtualMachine.VirtualMachine;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine(1,2);
        vm.interpretACommand( "AD11");
        System.out.println(vm.toString());
        vm.interpretACommand( "RR  ");
        System.out.println(vm.toString());
        vm.interpretACommand( "RR  ");
        System.out.println(vm.toString());
        vm.interpretACommand( "DI11");
        System.out.println(vm.toString());
        vm.interpretACommand( "Mmmm");
        System.out.println(vm.toString());



    }
}
