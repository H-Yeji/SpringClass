package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.*;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 난 서비스얌 + 싱글톤 객체로 생성해줄게
//@RequiredArgsConstructor // 생성자(public MemberService) 자동 생성
@Transactional(readOnly = true)
// Transactional 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,
// 만약 예외가 발생시 롤백처리 자동화⭐
public class MemberService {

    // memberRepository 재할당 불가 -> final
//    private final MemberRepository memberRepository;

    // memberService가 호출됨고ㅏ 동시에 memberRepository 생성이 아니라,
    // memberService 생성자가 호출될 때 memberRepository를 만들기 위해 생성자 생성
//    @Autowired // 싱글톤 객체 주입 (DI) 받음
//    public MemberService(MemberMemoryRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }
//    @Autowired // MemberJdbcRepository만 갈아끼워주면 됨 (지금 jdbc 사용하는 중)
//    public MemberService(MemberJdbcRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }
//    @Autowired // MemberMybatisRepository 갈아끼워주면 됨 => MemberMybatisRepo에서 인터페이스 extends해줘야함 💡
//    public MemberService(MemberMybatisRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }
//    @Autowired // jpa 활용
//    public MemberService(MemberJpaRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }

    // 다형성 설계
//    private final MemberRepository memberRepository;
//    @Autowired // spring data jpa 활용
//    public MemberService(MemberSpringDataJpaRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }

    // 비다형성 설계
    private final MyMemberRepository memberRepository;
    @Autowired // 싱글톤 객체를 주입(di) 받는다는 것을 의미
    public MemberService(MyMemberRepository memberMemoryRepository) {
        this.memberRepository = memberMemoryRepository;
    }


    // service에서 controller 참조 -> 순환 참조 -> 실행이 안될 것
//    @Autowired
//    private MemberController memberController;

    /**
     * 회원 가입
     */
    @Transactional
    public void memberCreate(MemberReqDto dto) {

        // 왼쪽 인터페이스, 오른쪽 구현체
        //MemberRepository memberRepository = new MemberMemoryRepository();

        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 넘 짧음‼️");
        }
        // dto를 Member 객체로 변환해서 db(repository)에 넘겨줌
        // 왜냐면 dto는 객체가 아니기 때문
//        Member member = new Member();
//        member.setName(dto.getName());
//        member.setEmail(dto.getEmail());
//        member.setPassword(dto.getPassword());

        // dto > toEntity 메서드 활용
        Member member = dto.toEntity(); // 객체 메서드 호출 (매우 간단쓰.. )
        memberRepository.save(member);
        // transactional 롤백처리 테스트
//        if (member.getName().equals("kim")) {
//            throw new IllegalArgumentException("잘못된 입력입니다.");
//        } // 여기서 예외가 터지면 위에서 한 save가 취소됨
    }

    /**
     * 회원 상세 조회
     */
    public MemberDetailResDto memberDetail(Long id) {

        // springdata jpa 쓰기 위해서 optional로 리턴타입 바꾸기
        Optional<Member> findMember = memberRepository.findById(id);

        //MemberDetailResDto dto = new MemberDetailResDto();
        // 여기서 예외 강제로 터뜨림 목적
        // (1) 적절한 트랜잭션 롤백처리 하려고 => 전제조건) 트랜잭션 어노테이션이 붙어있어야 함
        // (2) 클라이언트한테 적절한 메시지 + 적절한 상태 코드 줄 수 있음
        Member member = findMember.orElseThrow(()->new EntityNotFoundException("없는 회원 입니다."));

        // 아래 코드 -> Member에 detFromEntity 생성하면서 주석처리함
//        dto.setId(member.getId());
//        dto.setName(member.getName());
//        dto.setEmail(member.getEmail());
//        dto.setPassword(member.getPassword());

//        LocalDateTime createdTime = member.getCreatedTime();
//        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
//                createdTime.getDayOfMonth() + "일";
//        memberDetailResDto.setDateTime(date);

        System.out.println("글쓴이의 쓴글 개수" + member.getPosts().size());
        for (Post p : member.getPosts()) {
            System.out.println("글의 제목 " + p.getTitle());
        }
        MemberDetailResDto memberDetailResDto = member.detFromEntity();
        return memberDetailResDto;
    }

    /**
     * 회원 목록 조회
     */
    public List<MemberResDto> memberList() {

        List<Member> memberList = memberRepository.findAll();

        List<MemberResDto> memberResDtoList = new ArrayList<>();
        int j = 0;
        for (Member member : memberList) {
//            memberResDtoList.add(new MemberResDto());
//            memberResDtoList.get(j).setId(member.getId());
//            memberResDtoList.get(j).setName(member.getName());
//            memberResDtoList.get(j).setEmail(member.getEmail());

            MemberResDto memberResDto = member.listFromEntity();
            memberResDtoList.add(memberResDto);
            j++;

            System.out.println(memberResDto);
        }
        return memberResDtoList;
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void pwUpdate(MemberUpdateDto dto) {

        // 회원 가지고와서 바꿔주기
        Member member = memberRepository.findById(dto.getId()).orElseThrow(()->
                new EntityNotFoundException("member is not found"));

        member.updatePwd(dto.getPassword()); // 메서드 실행
        System.out.println(member.getPassword());
        System.out.println(member.getId());
        // 기존 객체를 조회 후 수정한 다음 save하면 jpa가 알아서 update 진행
        memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("member is not found"));
        memberRepository.delete(member);

        // member.updateDelYn("Y"); // 실무에선 이렇게
        // memberRepository.delete(member);
    }

}
