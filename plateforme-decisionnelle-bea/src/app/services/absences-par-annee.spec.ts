import { TestBed } from '@angular/core/testing';

import { AbsencesParAnnee } from './absences-par-annee';

describe('AbsencesParAnnee', () => {
  let service: AbsencesParAnnee;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbsencesParAnnee);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
