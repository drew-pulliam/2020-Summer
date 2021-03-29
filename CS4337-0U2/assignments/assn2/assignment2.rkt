#lang scheme
; Drew Pulliam
; DTP180003
; CS4337.0U2

; question 5
(define (sumeven lis)
    (cond
        ((null? lis) 0)
        ((not(list? lis)) 0)
        (else(cond
            ((not(list? (car lis)))
                (cond
                  ((even? (car lis)) (+ (car lis) (sumeven (cdr lis))))
                  (else (sumeven (cdr lis)))
                )
            )
            (else (+ (sumeven (car lis)) (sumeven (cdr lis))))
        ))
    )
)

; question 6
(define (deleteitem lis)
    (cons (car lis) (cons (cadr lis) (cdddr lis)))
)

; question 7
(define (newlist lis)
  (cond
    ((null? lis) '())
    ((null? (cdr lis)) lis)
    (else (cons (cadr lis) (cons (car lis) (newlist(cddr lis))) ))
  )
)

; question 8
(define (leaves lis)
  (cond 
    ((null? lis) lis)
    ((not (pair? lis)) (list lis))
    (else (append (leaves (cdr lis)) (leaves (car lis))))
  )
)

; question 9
(define (EXP-DEPTH lis)
  (cond 
    ((null? lis) 0)
    ((not(list? lis )) 0)
    (else (max (+ 1 (EXP-DEPTH(car lis))) (EXP-DEPTH(cdr lis)) ))
  )
)

; question 10
(define (subsets lis)
  (if (null? lis) '(())
    (append (subsets (cdr lis)) (map (lambda (subset) (cons (car lis) subset)) (subsets (cdr lis))))
  )
)
