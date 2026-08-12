public class EnumSwitch {

    enum LoginChannel {
        FACEBOOK, KAKAO, APPLE, NAVER, DEFAULT;

        public boolean isSocial() {
            return this == FACEBOOK || this == KAKAO || this == NAVER;
        }
    }

    static void facebookLogin() {
        System.out.println("페이스북으로 로그인합니다.");
    }

    static void kakaoLogin() {
        System.out.println("카카오로 로그인합니다.");
    }

    static void appleLogin() {
        System.out.println("애플로 로그인합니다.");
    }

    static void naverLogin() {
        System.out.println("네이버로 로그인합니다.");
    }

    static void defaultLogin() {
        System.out.println("기본 로그인 방식을 사용합니다.");
    }

    static void executeLogin(LoginChannel channel) {
        switch (channel) {
            case FACEBOOK -> facebookLogin();
            case KAKAO -> kakaoLogin();
            case APPLE -> appleLogin();
            case NAVER -> naverLogin();
            case DEFAULT -> defaultLogin();
        }
    }

    public static void main(String[] args) {
        for (LoginChannel channel : LoginChannel.values()) {
            executeLogin(channel);
            if (channel.isSocial()) {
                System.out.println(channel + "은(는) 소셜 로그인입니다.");
            }
        }
    }
}
