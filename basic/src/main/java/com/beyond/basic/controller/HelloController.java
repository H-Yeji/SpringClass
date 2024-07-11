package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller //얘는 컨트롤러얌 - 사용자의 요청을 처리하고 응답하는 편의기능임을 명시
//@RestController // Controller + 각 메서드마다 @ResponseBody
@RequestMapping("/hello") // 공통된 url -> 클래스 차원에 url 매핑시키기 위해 사용
public class HelloController {

    // ===========================get============================
    /**
     * (1) 사용자가 서버에게 화면 요청
     * (2) ResponseBody가 붙으면 단순 String 데이터 return (get)
     */
    @GetMapping("") //getMapping을 통해 get 요청 처리 + url 패턴 명시
    //@ResponseBody //return한 문자열 화면에 찍어서 응답 주께 (화면이 아니라, 데이터를 반환)
    // 만약 여기서 responseBody가 없으면 스프링은 templates > helloworld.html을 찾아서 return을 시도함⭐
    public String helloWorld() {
        return "helloworld";
    }

    /**
     * (3) 사용자가 json 데이터 요청 (get)
     * : data를 return하되, json형식으로 return
     */
    @GetMapping("/json")
    // @RequestMapping(value = "/json", method = RequestMethod.GET) // getMapping대신 메서드 차원에서 이렇게 작성해도 됨
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();

        hello.setName("yeji");
        hello.setEmail("yeji@test.com");

        //json으로 바꿔주기
//        ObjectMapper objectMapper = new ObjectMapper();
//        String returnValue = objectMapper.writeValueAsString(hello);
//        return returnValue; // 이 방법 대신 반환 타입 > Hello로 바꿔서 진행

        return hello; // 이러헥 하면 objectMapper 안써도 알아서 직렬화
    }

    /**
     * (4) 사용자가 json 데이터를  요청하되, parameter 형식으로 특정 객체 요청
     * parameter => name=yeji&email=yeji@test.com
     */
    @GetMapping("/param1")
    @ResponseBody // json 이니까 필요
    public Hello param1(@RequestParam(value = "name") String name,
                        @RequestParam(value = "email") String email) {

        Hello hello = new Hello();
        hello.setName(name);
        hello.setEmail(email);
        System.out.println(hello);
        return hello;
    }

    /**
     * (5) parameter 형식으로 요청하되, 서버에서 데이터 바인딩하는 형식
     * 파라미터가 많을 경우 객체로 대체가 가능 (파라미터를 Hello)
     * 객체의 각 변수에 맞게 알아서 매핑됨 (= 데이터 바인딩)
     *
     * 요청 ) ?name=xxx&email=xxx&password=xxx
     * 데이터 바인딩 조건
     * (1) 기본 생성자 반드시 있어야 함 (2) setter가 있어야함
     * -> 그래서 어노테이션 쓸 거면 entity에 NoArgs 어쩌고 꼭 있어야함
     */
    @GetMapping("/param3")
    @ResponseBody
    public Hello param3(Hello hello) {
        return hello;
    }

    /**
     * (6) 서버에서 화면에 데이터를 넣어 사용자에게 return하는 형식 (model 객체 사용)
     */
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName, Model model) {
        // model 객체에 name이라는 키값에 value를 세팅하면 해당 key:value는 화면으로 전달
        model.addAttribute("name", inputName);

        return "helloworld";
    }

    /**
     * (7) pathvariable 방식을 통해 사용자로부터 값을 받아 화면을 return
     * pathvariable : localhos:8080/hello/model-path/yeji : yeji 사용자 데이터 조라
     * pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로,
     * 좀 더 restful한 방식임
     */
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String name, Model model) { // {name}의 name과 여기 name과 같음

        model.addAttribute("name", name); // key, value
        return "helloworld";
    }

    // ===========================post============================
    /**
     * post 요청 (사용자 입장에서 서버에 데이터를 주는 상황)
     * (1) url 인코딩 방식 (text만 전송)
     */
    @GetMapping("/form-view") // 일단 화면을 get으로 뿌려야함
    public String formView() {
        return "formView";
    }

    // 여기 post 주석건 이유는 아래formPost2 메서드도 post에 url까지 똑같이 작성해놔서야 알겠지 수여나 ?
    // 만약에 formPost1 메서드를 실행하고 싶으면 아래 formPost2의 post를 추석처리하고, 여기는 주석 해제해야해 ! 알게찌 수여나?
    //@PostMapping("/form-view") // 일단 화면을 get으로 뿌려야함
    // 형식 : name=xxx&email=xxx&password=xxx 형식으로 들어오기 때문에 requestParam 사용할 수 있음
    @ResponseBody
    public String formPost1(@RequestParam(value = "name") String name,
                            @RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password) {

        Hello hello = new Hello();
        hello.setName(name);
        hello.setEmail(email);
        hello.setPassword(password);

        System.out.println(hello);
        return "ok";
    }

    // 파라미터 너무 길어 -> 객체로 변경하고싶엉 !
    @PostMapping("/form-view") // 일단 화면을 get으로 뿌려야함
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello) { // ModelAttribute 생략 가능

        System.out.println(hello); // toString 메서드 내장되어있어서 그냥 출력됨

        return "ok";
    }

    /**
     * (2) multipart/form-data 방식 (텍스트 + 파일 전송)
     */
    @GetMapping("/form-file-post")
    public String formFileGet() {
        return "formFileView";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFilePost(@ModelAttribute Hello hello,
                               @RequestParam(value = "photo")MultipartFile photo) { // html에서 name이 photo로 들어감

        System.out.println(hello);
        System.out.println(photo.getOriginalFilename()); // 파일 이름찍어서 잘 들어왔는지 확인
        return "ok";
    }

    /**
     * (3) js를 활용한 form 데이터 전송 (text)
     */
    // name, email, password 전송 (js 사용)
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
        return "axiosFormView";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    public String axiosFormPost(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    /**
     * (4) js를 활용해서 form 데이터 전송 (+파일)
     */
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView() {
        return "axiosFormFileView";
    }


    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(@ModelAttribute Hello hello,
                                        @RequestParam(value = "file") MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    /**
     * (5) js를 활용한 json 데이터 전송
     */
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axiosJsonView";
    }

    // 이전처럼 @ModelAttribute Hello hello 하는방식은 json에서 제대로 데이터 바인딩이 안됨 (null출력)
    // 왜냐면 ? 위 방법은 "파라미터"로 값을 받아오는 방식에서 사용하는 것이기 때문임
    // 그럼 json으로 전송한 데이터는 ? => "@RequestBody"를 활용해야 함 ⭐
    @PostMapping("/axios-json-view")
    @ResponseBody
    public String axiosJsonPost(@RequestBody Hello hello) {
        System.out.println(hello);
        return "ok";
    }


    /**
     * (6) js를 활용한 json 데이터 전송 (+파일) => json + file
     */
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView() {
        return "axiosJsonFileView";
    }

    // @RequestPart : 파일과 json을 처리할 때 주로 사용
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    // @RequestParam(value = "hello") String hello,
    // @RequestParam(value = "file") MultipartFile file) throws JsonProcessingException
    public String axiosJsonFilePost(@RequestPart("hello") Hello hello,
                                    @RequestPart("file") MultipartFile file){
        System.out.println("hello: " + hello);
        //System.out.println("file: " + file.getOriginalFilename());

        // String으로 받은 뒤, 수동으로 객체로 변환 (RequestParam 사용하는 방법 쓸 때)
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(hello, Hello.class);
//        System.out.println(h1.getName());
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    /**
     * (7) js를 활용한 json 데이터 전송인데 파일 여러개
     */
    @GetMapping("/axios-json-multifile-view")
    public String axiosJsonMultifileView() {
        return "axiosJsonMultifileView";
    }

    @PostMapping("/axios-json-multifile-view")
    @ResponseBody
    public String axiosJsonMultifilePost(@RequestPart("hello") Hello hello,
                                         @RequestPart("files") List<MultipartFile> files) {

        System.out.println(hello);
        for(MultipartFile file: files) {
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }


    /**
     *  (8) 중첩된 json 데이터 처리
     *  {name: 'yeji', email: 'yeji@test.com', scores: [{math:60}, {music:70}, {english:50}]}
     */
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axiosNestedJsonView";
    }


    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student) {

        System.out.println(student);
        return "ok";
    }


}
