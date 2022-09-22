package com.codersee.service

import com.codersee.model.Employee
import com.codersee.repository.EmployeeRepository
import com.codersee.request.EmployeeRequest
import jakarta.inject.Singleton

@Singleton
class EmployeeService(employeeRepository: EmployeeRepository, private val companyService: CompanyService) :
    GenericService<Employee, EmployeeRequest>(employeeRepository) {

    override var entityName = "Employee"

    override fun createModelInstanceFromRequest(request: EmployeeRequest): Employee {
        val company = request.companyId?.let { companyService.findById(it) }

        return Employee(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            company = company
        )
    }

}