package com.library.library_management_system.service;

import com.library.library_management_system.dto.MemberResponseDTO;
import com.library.library_management_system.entity.Member;
import com.library.library_management_system.exception.DuplicateEmailException;
import com.library.library_management_system.exception.MemberNotFoundException;
import com.library.library_management_system.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {
    private  final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public Member addMember(Member member) {
        if(memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateEmailException(
                    "member with email "+member.getEmail()+"  already exists");
        }
        return memberRepository.save(member);
    }
    public Member  getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(()->
                new MemberNotFoundException("member not found "+id)
        );
    }
    public List<Member>  getAllMembers() {
        return memberRepository.findAll();
    }
    public Member updateMember(Long id, Member updatedMember) {

        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found with id: " + id
                        )
                );

        if (memberRepository.existsByEmailAndIdNot(
                updatedMember.getEmail(), id)) {

            throw new DuplicateEmailException(
                    "Member with email " +
                            updatedMember.getEmail() +
                            " already exists"
            );
        }

        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhoneNumber(updatedMember.getPhoneNumber());

        return memberRepository.save(existingMember);
    }
    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found with id: " + id
                        )
                );

        memberRepository.delete(member);
    }

}
