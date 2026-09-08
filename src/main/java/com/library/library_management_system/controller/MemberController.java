package com.library.library_management_system.controller;

import com.library.library_management_system.dto.MemberRequestDTO;
import com.library.library_management_system.dto.MemberResponseDTO;
import com.library.library_management_system.mapper.MemberMapper;
import com.library.library_management_system.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.library.library_management_system.entity.Member;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
     public MemberController(MemberService memberService, MemberMapper memberMapper) {
         this.memberService = memberService;
         this.memberMapper = memberMapper;
     }
   @PostMapping
    public ResponseEntity<MemberResponseDTO> addMember(@Valid @RequestBody MemberRequestDTO dto){
         Member member = memberMapper.toEntity(dto);
         Member savedMember=memberService.addMember(member);
         MemberResponseDTO response=memberMapper.toResponseDTO(savedMember);
         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(response);
   }
   @GetMapping("/{id}")
public ResponseEntity<MemberResponseDTO> getById( @PathVariable("id") Long id){
         Member member=memberService.getMemberById(id);
         MemberResponseDTO response=memberMapper.toResponseDTO(member);
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(response);
}
@GetMapping
public  ResponseEntity getAllMembers(){
        List<Member> members=memberService.getAllMembers();
        List<MemberResponseDTO> response=members.stream()
        .map(memberMapper::toResponseDTO)
        .toList();
        return ResponseEntity.ok(response);
}
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDTO dto) {

        Member member = memberMapper.toEntity(dto);

        Member updatedMember =
                memberService.updateMember(id, member);

        MemberResponseDTO response =
                memberMapper.toResponseDTO(updatedMember);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {

        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}
