# data-masker
애플리케이션에서 개인정보 보호를 위한 데이터 마스킹 라이브러리입니다

## 프로젝트 개요
Java 애플리케이션에서 이메일, 전화번호, 주민등록번호 등과 같은 민감한 정보를 포함한 데이터를 마스킹하여 데이터베이스, 로그 파일 또는 애플리케이션 내에서의 노출을 방지합니다. 정규 표현식을 포함한 커스터마이징 가능한 마스킹 규칙도 지원합니다.

## 설치 방법
[data-masker](https://github.com/sohyeong-dev/data-masker) 라이브러리를 프로젝트에 포함시키기 위해서는 [JitPack](https://jitpack.io)을 사용합니다.

### Maven

`pom.xml` 파일에 다음의 리포지토리와 의존성을 추가하세요:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.sohyeong-dev</groupId>
        <artifactId>data-masker</artifactId>
        <version>v0.2.0</version>
    </dependency>
</dependencies>
```

### Gradle

`build.gradle` 파일에 JitPack 리포지토리를 추가하고, 의존성을 추가하세요:

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.sohyeong-dev:data-masker:v0.2.0'
}
```

## 사용 방법

### 1. 간단한 사용 예시

데이터 필드에 @Masked 애노테이션을 추가하여 민감한 정보를 자동으로 마스킹할 수 있습니다:

```java
import com.fastcampus.innercircle.datamasking.Masked;

public class User {
    @Masked(maskingType = Masked.MaskingType.RRN)
    private String rrn; // 주민등록번호

    @Masked(maskingType = Masked.MaskingType.EMAIL)
    private String email; // 이메일

    @Masked(maskingType = Masked.MaskingType.CUSTOM, regex = "^\\d{4}(?=-\\d{4})")
    private String ext; // 커스텀 마스킹 값

    // Getters and Setters
}
```

### 2. 프로그램 내에서 마스킹 적용

프로그램 내에서 직접 마스킹을 적용할 수도 있습니다:

```java
import com.fastcampus.innercircle.datamasking.DataMasker;
import com.fastcampus.innercircle.datamasking.Masked;

public class Main {
    public static void main(String[] args) {
        Masked masked = new MaskedImpl(Masked.MaskingType.EMAIL, 0, 0, '*', "", "");
        String email = "user@example.com";
        String maskedEmail = DataMasker.applyMask(email, masked);
        System.out.println(maskedEmail);  // 출력: us**@example.com
    }
}
```

## License

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
