package com.codersee.service

import com.codersee.model.Company
import com.codersee.repository.CompanyRepository
import com.codersee.repository.EmployeeRepository
import com.codersee.request.CompanyRequest
import jakarta.inject.Singleton

@Singleton
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) :
    GenericService<Company, CompanyRequest>(companyRepository) {

    override var entityName = "Company"

    override fun update(id: String, request: CompanyRequest): Company {
        val updateResult = companyRepository.update(
            id = id,
            update = Company(name = request.name, address = request.address)
        )

        if (updateResult.modifiedCount == 0L)
            throw throw RuntimeException("Company with id $id was not updated")

        val updatedCompany = findById(id)
        updateCompanyEmployees(updatedCompany)

        return updatedCompany
    }

    private fun updateCompanyEmployees(updatedCompany: Company) {
        employeeRepository
            .findAllByCompanyId(updatedCompany.id!!.toHexString())
            .map {
                employeeRepository.update(
                    it.id!!.toHexString(),
                    it.apply { it.company = updatedCompany }
                )
            }
    }

    override fun createModelInstanceFromRequest(request: CompanyRequest): Company {
        return Company(
            name = request.name,
            address = request.address
        )
    }
}