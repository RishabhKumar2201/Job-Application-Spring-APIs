package com.example.demo.company.impl;

import com.example.demo.company.Company;
import com.example.demo.company.CompanyRepository;
import com.example.demo.company.CompanyService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    public CompanyServiceImpl(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company updateCompany, Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            Company companyToUpdate = optionalCompany.get();
            companyToUpdate.setDescription(updateCompany.getDescription());
            companyToUpdate.setName((updateCompany.getName()));
            companyToUpdate.setJobs(updateCompany.getJobs());
            companyRepository.save(companyToUpdate);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean addCompany(Company company) {
        try {
            companyRepository.save(company);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            return optionalCompany.get();
        }
        else {
            return null;
        }
    }
}
