describe('template spec', () => {
  it('passes', () => {
    cy.viewport(1920,1080)
    cy.visit('http://localhost:5173/')
    cy.get('.login-button').click();
    cy.get('.email-box').type('q@qmail.com');
    cy.get('.password-box').type('q');
    cy.get('.login-button').click();
  })
})