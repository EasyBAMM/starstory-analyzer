# starstory-analyzer
커스텀 검색엔진 플러그인

## 웹 다운로드 설치방법
>~~~~
>검색엔진폴더/bin/elasticsearch-plugin install http://URL/starstory-analyzer-1.0.0.zip
>

## 웹 다운로드 설치방법
>~~~~
>검색엔진폴더/bin/elasticsearch-plugin install file:///파일경로/starstory-analyzer-1.0.0.zip
>


## 빌드 방법
>~~~
> gradle clean build buildPluginZip
>
해당 파일은 build/distributions 폴더에 starstory-analyzer-1.0.0.zip 파일로 되어있음

======

# 제공기능

엘라스틱서치 혹은 솔라의 최신버전에서 사용가능한 한글기반의 자동완성/검색결과를 더욱 효율적으로 사용하기 위해 개발된 플러그인 이며 아래와 같은 기능을 제공합니다. 

------
## 초성추출
검색어로 들어오는 단어가 초성인 경우 검색 결과 혹은 자동완성의 결과를 초성으로 매칭하여 검색되게 하는 플러그인 입니다. 

### 사용방법
```json
{
    "tokenizer": "standard",
    "filter": [ "lowercase", "chosung_filter" ],
    "text": "초성테스트"
}
```

### 결과
```json
{  
  "tokens": [  
    {  
      "token": "ㅊㅅㅌㅅㅌ",
      "start_offset": 0,
      "end_offset": 5,
      "type": "<HANGUL>",
      "position": 0
    }
  ]
}
```
------
## 자소분해
자동완성에서 한글을 검색 가능한 형태로 변형하는 플러그인 입니다. 예를 들어 삼성전자의 경우 삼ㅅ만 검색하여도 삼성전자가 검색 될수 있도록 한글의 자소를 분해하여 검색 할 수 있도록 합니다. 

### 사용방법
```json
{
    "tokenizer": "standard",
    "filter": [ "lowercase", "jamo_filter" ],
    "text": "자모테스트"
}
```

### 결과
```json
{  
  "tokens": [  
    {  
      "token": "ㅈㅏㅁㅗㅌㅔㅅㅡㅌㅡ",
      "start_offset": 0,
      "end_offset": 5,
      "type": "<HANGUL>",
      "position": 0
    }
  ]
}
```

------
## 한영/영한 오타교정
한글을 영문으로, 영문을 한글로 검색한 결과를 보정해주는 플러그인 입니다. 예를들어 삼성전자를 tkatjdwjswk 라고 검색하거나 ㅑㅔㅙㅜㄷ와 같이 iphone 을 잘못 검색한 경우 검색 결과를 도출 할수 있도록 도와줍니다. 
해당 필터 사용시 filter에서 word delimiter(https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-word-delimiter-tokenfilter.html)로 한글/영문/숫자를 분리 한다음 사용하는것이 복합언어에 사용하기 좋음
많이 사용하는 word delimiter 필터 방법
```json
{
    "word_split_delimiter": {
    "split_on_numerics": "true",
    "type": "word_delimiter",
    "catenate_numbers": "false",
    "stem_english_possessive": "false",
    "catenate_words": "false"
    }
}
```
### 사용방법

#### 영한오타 사용방법
```json
{
    "tokenizer": "standard",
    "filter": [ "lowercase", "eng2kor_filter" ],
    "text": "dudansxptmxm"
}
```

#### 결과
```json
{  
  "tokens": [  
    {  
      "token": "영문테스트",
      "start_offset": 0,
      "end_offset": 12,
      "type": "<ALPHANUM>",
      "position": 0
    }
  ]
}
```

#### 한영 오타 사용방법
```json
{
  "tokenizer": "standard",
  "filter": [ "kor2eng_filter", "lowercase" ],
  "text": "두히ㅑ노 ㅆㄷㄴㅅ"
}
```

#### 결과
```json
{  
  "tokens": [  
    {  
      "token": "english",
      "start_offset": 0,
      "end_offset": 4,
      "type": "<HANGUL>",
      "position": 0
    },
    {  
      "token": "test",
      "start_offset": 5,
      "end_offset": 9,
      "type": "<HANGUL>",
      "position": 1
    }
  ]
}
```


------
## Soundex 한글/영문 필터
오타를 특정 묶음의 룰로 교정하거나 영문 soundex 알고리즘 로직으로 인코딩합니다.

### 한글 사용방법
```json
{
    "tokenizer": "standard",
    "filter": [
        "soundex_filter",
        "lowercase"
    ],
    "text": "무싄사"
}
```

### 결과
```json
{  
  "tokens": [  
    {  
      "token": "ㅁㅜㅅㅣㄴㅅㅏ",
      "start_offset": 0,
      "end_offset": 3,
      "type": "<HANGUL>",
      "position": 0
    }
  ]
}
```
