package io.spring.messenger.web

import io.spring.messenger.domain.User
import io.spring.messenger.repository.UserRepository
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*

@RestController @RequestMapping("/user")
class UserController @Autowired constructor(val repository: UserRepository) {

    @GetMapping
    fun findAll() = repository.findAll()

    @PostMapping
    fun create(@RequestBody user: User) = repository.create(user)

    @PutMapping("/{userName}/location/{x},{y}") @ResponseStatus(NO_CONTENT)
    fun updateLocation(@PathVariable userName:String, @PathVariable x: Double, @PathVariable y: Double)
            = repository.updateLocation(userName, Point(x, y))

    @GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
    fun findByBoundingBox(@PathVariable userName:String, @PathVariable xMin:Double,
                          @PathVariable yMin:Double, @PathVariable xMax:Double, @PathVariable yMax:Double)
            = repository.findByBoundingBox(PGbox2d(Point(xMin, yMin), Point(xMax, yMax)))

}