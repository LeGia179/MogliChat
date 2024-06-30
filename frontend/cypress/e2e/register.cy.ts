describe('template spec', () => {
    it('passes', () => {
        cy.viewport(1920,1080)
        cy.visit('http://localhost:5173/')
        cy.get('.register-button').click();
        cy.get('.username-box').type('test');
        cy.get('.email-box').type('test@gmail.com');
        cy.get('.password-box').type('test');
        cy.get('.register-button').click();

    })
})