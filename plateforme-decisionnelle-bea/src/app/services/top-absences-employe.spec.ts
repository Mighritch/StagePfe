import { TestBed } from '@angular/core/testing';

import { TopAbsencesEmploye } from './top-absences-employe';

describe('TopAbsencesEmploye', () => {
  let service: TopAbsencesEmploye;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TopAbsencesEmploye);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
