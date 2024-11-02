package com.example.demo.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<List<Company>>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@RequestBody Company company, @PathVariable Long id){
        boolean isUpdateedCompany = companyService.updateCompany(company, id);
        if(isUpdateedCompany) {
            return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Cpmany not updated", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        boolean isAddedCompany = companyService.addCompany(company);
        if(isAddedCompany) {
            return new ResponseEntity<>("Company added successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Could not be added", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        boolean isDeleted = companyService.deleteCompanyById(id);
        if(isDeleted){
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        if(company != null){
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
