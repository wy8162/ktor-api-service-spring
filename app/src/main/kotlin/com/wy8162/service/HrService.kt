package com.wy8162.service

import arrow.core.Either
import arrow.core.rightIfNotNull
import com.wy8162.error.ApiError
import com.wy8162.error.ErrorCode
import com.wy8162.model.hr.Employee
import com.wy8162.model.hr.EmployeeEntity
import com.wy8162.model.hr.toEmployee
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Service

interface HrService {
    suspend fun getEmployee(employeeId: Int): Either<ApiError, Employee>
}

@Service
class HrServiceImpl(
    private val dbService: DatabaseService
) : HrService {
    override suspend fun getEmployee(employeeId: Int): Either<ApiError, Employee> = dbService.databaseQuery {
        EmployeeEntity.select {
            EmployeeEntity.employeeId eq employeeId
        }.singleOrNull().rightIfNotNull {
            ApiError(ErrorCode.ERR_DB_FAILURE, "Employee not found")
        }.map {
            it.toEmployee()
        }
    }
}
