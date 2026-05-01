import { TestBed } from '@angular/core/testing';

import { AbsencesParMotif } from './absences-par-motif';

describe('AbsencesParMotif', () => {
  let service: AbsencesParMotif;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbsencesParMotif);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
