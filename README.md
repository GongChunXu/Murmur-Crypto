README
===========================
# <center>murmur-crypto V1.0.7</center>
[![](https://jitpack.io/v/GongChunXu/murmur-crypto.svg)](https://jitpack.io/#GongChunXu/murmur-crypto)

****
## 目录
* [介绍](#介绍)
* [功能](#功能)
* [使用方式](#使用方式)
___

## 介绍
Murmur-Crypto 用于Api加解密
___
## 功能

| 语法|说明|
|---|--|
|`@MurMurDecrypt`|用于解密请求参数体|
|`@MurMurEncrypt`|用于加密响应数据|
___
## 使用方式
### Step 1. Add the JitPack repository to your build file(pom.xml)
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
### Step 2. Add the dependency
```
<dependency>
    <groupId>com.github.GongChunXu</groupId>
    <artifactId>murmur-crypto</artifactId>
    <version>1.0.7</version>
</dependency>
```

### Step 3. application.yml 中增加如下配置(※顶头写，顶头写，顶头写。正好说三遍)
```
murmur-crypto:
  # enabled 这么说吧，它暂时没用，啥用没有
  enabled: true
  # 加密公钥（对应前端解密用私钥private-key：abcxxxxxxx123）
  public-key: ENCxxxxxxxx78901234567890
  # 解密私钥（对应前端加密用公钥private-key：bbcxxxxxxx123）
  private-key: cNCxxxxxxxx77701234567890
  # 是否输入日志 true：输出 false：不输出
  log: true
```

### Step 示例
```java
/**
 * 注解加密
 */
@MurMurEncrypt
@PostMapping("/RSAEncrypt")
public MurMurResult RSAEncrypt() {
  MurMurMap data = new MurMurMap();
  data.put("name", "张三");
  data.put("age", 18);
  return MurMurResult.ok(data);
}

```

```java
/**
 * 注解解密
 */
@MurMurDecrypt
@PostMapping("/RSADecrypt")
public MurMurResult RSADecrypt(@RequestBody Map<String, Object> map) {
  return MurMurResult.ok(map.toString());
}

```

### Step 生成配置文件中的公私密钥对(自己代码任意移除调用即可)
```java
Map<String, String> rsa = MurMurCryptoUtils.generateRsaBase64();
```



