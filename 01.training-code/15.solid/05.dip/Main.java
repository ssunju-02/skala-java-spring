public class Main {
    public static void main(String[] args) {
        // 조립(Composition Root)에서만 구현체를 선택
        InputDevice keyboard = new Keyboard();
        OutputDevice monitor = new Monitor();
        Computer computer = new Computer(keyboard, monitor);
        computer.operate();

        // 새로운 입력/출력 장치를 추가해도 Computer 클래스는 수정 불필요
        InputDevice wirelessKeyboard = new WirelessKeyboard();
        OutputDevice wirelessDisplay = new WirelessDisplay();
        Computer wirelessComputer = new Computer(wirelessKeyboard, wirelessDisplay);
        wirelessComputer.operate();
    }
}
